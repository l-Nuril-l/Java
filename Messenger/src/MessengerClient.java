import Models.*;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.List;

public class MessengerClient extends JFrame {

    protected JFrame callFrame = null;
    private JButton btnSaveFile = null;
    private JButton btnOpenDir = null;
    private JButton btnFileFilter = null;
    private JButton btnName = null;
    private JList<Message> listOfMessages = null;
    private JScrollPane ls = null;
    private JList<ChatRoom> listOfChats = null;
    private JList<User> listOfUsers = null;
    private JPopupMenu popupOfListOfMessages = null;
    private JPopupMenu popupOfOfDirect = null;
    private JPopupMenu popupOfOfGroup = null;
    private JTextField tfMessage = null;
    private JMenuItem popupMsgItemDm = null;
    private JMenuItem popupMsgItemCreateGroup = null;
    private JMenuItem popupMsgItemCall = null;
    private JMenu popupMsgMenuInvite = null;
    private JButton btnSendMsg = null;

    private ClientListener listener = null;

    private DefaultListModel<ChatRoom> chats = null;

    private User user = null;


    private Thread t = null;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private JFileChooser fileChooser = null;

    private SourceDataLine speakers = null;

    private final String[][] FILTERS = {{"docx", "Файлы Word (*.docx)"},
            {"pdf", "Adobe Reader(*.pdf)"}};


    public void scrollMsgJListToEnd()
    {
        int lastIndex = listOfMessages.getModel().getSize() - 1;
        if (lastIndex >= 0) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    listOfMessages.ensureIndexIsVisible(lastIndex);
                }
            });

        }
    };

    public ChatRoom getChatByUUID() {
        return getChatByUUID(null);
    }

    public UUID getUUIDCurrentChat() {
        return chats.get(listOfChats.getSelectedIndex()).getId();
    }

    public ChatRoom getChatByUUID(UUID id) {
        if (id == null)
            return chats.get(0);
        for (int i = 0; i < chats.size(); i++) {
            var it = chats.get(i);
            if (it.getId().equals(id))
                return it;
        }
        return null;
    }

    public void check(MouseEvent e, UUID id) {
        if (e.isPopupTrigger()) { //if the event shows the menu
            listOfMessages.setSelectedIndex(listOfMessages.locationToIndex(e.getPoint())); //select the item
            if (listOfMessages.getSelectedValue().hasFile() || listOfMessages.getSelectedValue().isSystem() || listOfMessages.getSelectedValue().getUser().getUuid().equals(id))
                return;

            popupMsgMenuInvite.removeAll();


            for (int i = 0; i < chats.size(); i++) {
                if (chats.get(i).getRoomType() == ROOM_TYPE.GROUP) {
                    var users = chats.get(i).getUsers();
                    boolean already = false;
                    for (int j = 0; j < users.size(); j++) {
                        if(listOfMessages.getSelectedValue().getUser().getUuid().equals(users.get(j).getUuid()))
                        {
                           already = true;
                           break;
                        }
                    }
                    if (already) break;


                    JMenuItem jmi = new JMenuItem(chats.get(i).getName());
                    popupMsgMenuInvite.add(jmi);
                    int finalI = i;
                    jmi.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                var users = chats.get(listOfChats.getSelectedIndex()).getUsers();
                                for (int j = 0; j < users.size(); j++) {
                                    if (users.get(j).getUuid().equals(listOfMessages.getSelectedValue().getUser().getUuid())) {
                                        out.writeObject(new Message(users.get(j), chats.get(finalI).getId(), MESSAGE_ACTION.ADD_USER_TO_GROUP));
                                        out.flush();
                                        return;
                                    }
                                }
                                showWarning("Данный пользователь покинул чат.");


                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                }
            }

            popupOfListOfMessages.show(listOfMessages, e.getX(), e.getY()); //and show the menu
        }
    }

    public void checkChats(MouseEvent e, UUID id) {
        if (e.isPopupTrigger()) { //if the event shows the menu
            listOfChats.setSelectedIndex(listOfChats.locationToIndex(e.getPoint())); //select the item
            if (listOfChats.getSelectedValue().getRoomType().equals(ROOM_TYPE.GROUP))
                popupOfOfGroup.show(listOfChats, e.getX(), e.getY()); //and show the menu
            if (listOfChats.getSelectedValue().getRoomType().equals(ROOM_TYPE.DIRECT))
                popupOfOfDirect.show(listOfChats, e.getX(), e.getY()); //and show the menu
        }
    }

    public MessengerClient() {
        super("Messenger ");



        setDefaultCloseOperation(EXIT_ON_CLOSE);
        user = new User();
        user.setName("Anonymous#" + (int) (Math.random() * 9999));
        setTitle(getTitle() + user.getName());

        // Кнопка создания диалогового окна для выбора директории
        btnOpenDir = new JButton("Отправить файл");
        // Кнопка создания диалогового окна для сохранения файла
        btnSaveFile = new JButton("Сохранить файл");
        btnFileFilter = new JButton("Переподключится");
        btnName = new JButton("Никнейм");


        popupOfListOfMessages = new JPopupMenu();

        popupOfOfGroup = new JPopupMenu();
        popupOfOfGroup.add("Переименовать").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var new_name = JOptionPane.showInputDialog("New group name", listOfChats.getSelectedValue().getName());
                if (new_name == null) return;
                if (new_name.isEmpty()) {
                    showWarning("Name field empty!");
                    return;
                }
                if (new_name.length() > 64) {
                    showWarning("Name length limit reached. Limit 64!");
                    return;
                }
                try {
                    out.writeObject(new Message(new_name, user, listOfChats.getSelectedValue().getId(), MESSAGE_ACTION.RENAME_GROUP));
                    out.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        popupOfOfGroup.add("Покинуть").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    out.writeObject(new Message(user, listOfChats.getSelectedValue().getId(), MESSAGE_ACTION.LEAVE_GROUP));
                    out.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                chats.remove(listOfChats.getSelectedIndex());
            }
        });

        popupOfOfDirect = new JPopupMenu();
        popupOfOfDirect.add("Покинуть").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chats.remove(listOfChats.getSelectedIndex());
            }
        });

        popupMsgItemDm = new JMenuItem("Личный чат");
        popupMsgItemCreateGroup = new JMenuItem("Создать группу");
        popupMsgItemCall = new JMenuItem("Позвонить");
        popupMsgMenuInvite = new JMenu("Добавить в группу");

        popupOfListOfMessages.add(popupMsgItemDm);
        popupOfListOfMessages.add(popupMsgItemCreateGroup);
        popupOfListOfMessages.add(popupMsgItemCall);
        popupOfListOfMessages.add(popupMsgMenuInvite);

        btnSendMsg = new JButton("Отправить");

        tfMessage = new JTextField();

        // Создание экземпляра JFileChooser
        fileChooser = new JFileChooser();


        // Размещение кнопок в интерфейсе
        JPanel contents = new JPanel();
        contents.setLayout(new GridLayout());
        contents.add(btnOpenDir);
        contents.add(btnSaveFile);
        contents.add(btnFileFilter);
        contents.add(btnName);
        add(contents, BorderLayout.NORTH);

        //middle
        {
            JPanel panel = new JPanel();
            GridBagLayout gbl = new GridBagLayout();
            gbl.rowHeights = new int[]{1};
            gbl.columnWidths = new int[]{1000, 3000, 1000};
            gbl.columnWeights = new double[]{1.0, 3.0, 1.0};
            gbl.rowWeights = new double[]{1.0};
            panel.setLayout(gbl);

            chats = new DefaultListModel<>();
            chats.add(chats.size(), new ChatRoom("Общий"));

            listOfMessages = new JList<>(getChatByUUID().getMessages());
            listOfMessages.setBackground(Color.BLACK);
            listOfMessages.setForeground(Color.WHITE);
            getChatByUUID(null).addMessage(new Message("Welcome! :)"));

            listOfChats = new JList<>(chats);
            listOfChats.setSelectedIndex(0);


            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(1, 1, 1, 1);
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.gridx = 0;
            gbc.gridy = 0;

            //listOfChats.setPreferredSize(new Dimension(110, 0));
            panel.add(new JScrollPane(listOfChats), gbc);
            gbc.gridx = 1;

            listOfUsers = new JList<>(getChatByUUID(null).getUsers());
            ls = new JScrollPane(listOfMessages);
            panel.add(ls, gbc);
            gbc.gridx = 2;

            //listOfUsers.setPreferredSize(new Dimension(110, 0));
            panel.add(new JScrollPane(listOfUsers), gbc);


            add(panel, BorderLayout.CENTER);
        }


        //bottom
        {
            GridBagConstraints constraints;
            JPanel input = new JPanel();
            input.setLayout(new GridBagLayout());
            constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.weightx = 100.0;
            constraints.fill = GridBagConstraints.BOTH;
            input.add(tfMessage, constraints);
            constraints.gridx = 1;
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            constraints.weightx = 1.0;
            input.add(btnSendMsg, constraints);
            add(input, BorderLayout.SOUTH);
        }

        // Подключение слушателей к кнопкам
        addFileChooserListeners();

        connect();

        // Вывод окна на экран
        setSize(650, 400);
        setVisible(true);
    }

    private void connect() {
        try {
            if (socket != null) {
                //if (socket.isConnected()) return;

                chats.clear();

                chats.add(chats.size(), new ChatRoom("Общий"));

                listOfChats.setSelectedIndex(0);

                socket.close();
            }
            socket = new Socket("176.36.16.46", 8888);
            out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            out.flush();
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

            out.writeObject(user.getName());
            out.flush();
            user.setUuid((UUID) in.readObject());

            getChatByUUID(null).setUsers((DefaultListModel<User>) in.readObject());
            listOfUsers.setModel(getChatByUUID(null).getUsers());
            listOfMessages.setSelectedIndex(0);

            listener = new ClientListener(this, in);
            if (t != null) t.stop();
            t = new Thread(listener);
            t.setDaemon(true);
            t.start();


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Oops...", JOptionPane.WARNING_MESSAGE, UIManager.getIcon("OptionPane.warningIcon"));
    }

    private void addFileChooserListeners() {
        //Попуп груп
        listOfChats.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                checkChats(e, user.getUuid());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                checkChats(e, user.getUuid());
            }
        });


        //Создать попуп
        listOfMessages.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                check(e, user.getUuid());
            }

            public void mouseReleased(MouseEvent e) {
                check(e, user.getUuid());
            }
        });

        //Переключение чата
        listOfChats.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (listOfChats.getSelectedIndex() != -1) {
                    listOfMessages.setModel(chats.get(listOfChats.getSelectedIndex()).getMessages());
                    listOfUsers.setModel(chats.get(listOfChats.getSelectedIndex()).getUsers());
                }
            }
        });

        //Создание личного чата на чате
        popupMsgItemDm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var sMsg = listOfMessages.getSelectedValue();
                if (getChatByUUID(sMsg.getUser().getUuid()) != null) {
                    System.out.println("Чат уже создан!");
                } else {
                    var users = chats.get(listOfChats.getSelectedIndex()).getUsers();
                    for (int j = 0; j < users.size(); j++) {
                        if (users.get(j).getUuid().equals(listOfMessages.getSelectedValue().getUser().getUuid())) {
                            chats.add(chats.size(), new ChatRoom(sMsg.getUser().getUuid(), new ArrayList<User>(Arrays.asList(user, users.get(j))), users.get(j).getName(), ROOM_TYPE.DIRECT));
                            return;
                        }
                    }
                    showWarning("Данный пользователь покинул чат.");
                }
            }
        });

        //Создание группы попуп на месседже
        popupMsgItemCreateGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var sMsg = listOfMessages.getSelectedValue();
                if (getChatByUUID(sMsg.getReceiver()) != null) {
                    System.out.println("Группа уже создан!");
                } else {
                    var users = chats.get(listOfChats.getSelectedIndex()).getUsers();
                    for (int j = 0; j < users.size(); j++) {
                        if (users.get(j).getUuid().equals(listOfMessages.getSelectedValue().getUser().getUuid())) {
                            Group group = new Group(UUID.randomUUID(), "Group#" + (int) (Math.random() * 9999), new ArrayList<>(Arrays.asList(user, users.get(j))));
                            try {
                                out.writeObject(new Message(user, sMsg.getUser().getUuid(), MESSAGE_ACTION.NEW_GROUP));
                                out.writeObject(group);
                                out.flush();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            chats.add(chats.size(), new ChatRoom(group.getId(), group.getUsers(), group.getName(), ROOM_TYPE.GROUP));
                            return;
                        }
                    }
                    showWarning("Данный пользователь покинул чат.");
                }
            }
        });

        //Звонок
        popupMsgItemCall.addActionListener( e -> {
            var targetUser = listOfMessages.getSelectedValue().getUser();
            showCallFrame(targetUser, 0);

                try {
                    out.writeObject(new Message(user, targetUser.getUuid() , MESSAGE_ACTION.CALL_REQUEST ));
                    out.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

        });

        btnName.addActionListener(e -> {
            var new_name = JOptionPane.showInputDialog("Username", user.getName());
            if (new_name == null) return;
            if (new_name.isEmpty()) {
                showWarning("Name field empty!");
                return;
            }
            if (new_name.length() > 64) {
                showWarning("Name length limit reached. Limit 64!");
                return;
            }
            user.setName(new_name);
            setTitle("Messenger -> " + user.getName());
            try {
                out.writeObject(new Message(new User(user.getName(), user.getUuid()), MESSAGE_ACTION.RENAME_USER));
                out.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });

        btnOpenDir.addActionListener(e -> {
            fileChooser.setDialogTitle("Выбор файла");
            // Определение режима - только файл
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(MessengerClient.this);
            // Если файт выбран, покажем ее в сообщении
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fileChooser.getSelectedFile();

                    var m = new Message("", file.getName(), user, getUUIDCurrentChat(), listOfChats.getSelectedValue().getRoomType());

                    out.writeObject(new Message("", file.getName(), user, getUUIDCurrentChat(), listOfChats.getSelectedValue().getRoomType()));

                    BufferedInputStream inBF = new BufferedInputStream(new FileInputStream(file));
                    byte[] bytes = new byte[32 * 1024];
                    int count;
                    long length = file.length();


                    out.writeLong(length);

                    long start = Calendar.getInstance().getTimeInMillis();
                    while ((count = inBF.read(bytes)) > -1) {
                        out.write(bytes, 0, count);
                    }

                    System.out.println("Отправленно за: " + (Calendar.getInstance().getTimeInMillis() - start));

                    inBF.close();
                    out.flush();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }

        });

        btnSaveFile.addActionListener(e -> {
            fileChooser.setDialogTitle("Сохранение файла");
            // Определение режима - только файл
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            var sm = listOfMessages.getSelectedValue();

            if (sm == null) {
                showWarning("Message not selected");
                return;
            }
            if (sm.getFile() == null || sm.getFile().isEmpty()) {
                showWarning("Message does not contain a file");
                return;
            }

            int result = fileChooser.showSaveDialog(MessengerClient.this);
            // Если файл выбран, то представим его в сообщении
            if (result != JFileChooser.APPROVE_OPTION || listOfMessages.getSelectedValue() == null) return;

            try {
                out.writeObject(new Message("", sm.getFile(), user, listOfChats.getSelectedValue().getId(), MESSAGE_ACTION.DOWNLOAD, listOfChats.getSelectedValue().getRoomType()));
                out.writeObject(fileChooser.getSelectedFile() + "\\" + sm.getFile());
                out.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }


        });

        btnFileFilter.addActionListener(e -> connect());


        tfMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });

        btnSendMsg.addActionListener(e -> {
            sendMessage();
        });

        //Пример фильтрации
        //FileNameExtensionFilter filter = new FileNameExtensionFilter(
        //            "Images", "png", "jpg");
        //   fileChooser.setFileFilter(filter);
    }

    private JButton btnEnd = null;
    private JButton btnAccept = null;
    private JButton btnReject = null;
    private JButton btnCalling = null;

    private void showCallFrame(User user, int type) {

        var target_uuid = user.getUuid();
        callFrame = new JFrame();
        callFrame.setSize(new Dimension(200,100));
        callFrame.setVisible(true);
        callFrame.setLayout(new BorderLayout());
        JPanel contents = new JPanel();
        contents.setLayout(new GridLayout());

        btnEnd = new JButton("Завершить звонок!");
        btnAccept = new JButton("Принять!");
        btnReject = new JButton("Отклонить!");
        btnCalling = new JButton("Отмена!");
        contents.add(btnEnd);
        contents.add(btnAccept);
        contents.add(btnReject);
        contents.add(btnCalling);
        callFrame.add(new Label(user.getName()),BorderLayout.NORTH);
        callFrame.add(contents,BorderLayout.CENTER);

        btnEnd.setVisible(false);
        switch (type)
        {
            case 0 -> {

                btnAccept.setVisible(false);
                btnReject.setVisible(false);
            }
            case 1 -> {
                btnCalling.setVisible(false);
            }
        }

        btnReject.addActionListener(event -> {
            try {
                out.writeObject(new Message(user, target_uuid , MESSAGE_ACTION.CALL_REJECT ));
                out.flush();
                callFrame.dispose();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        btnCalling.addActionListener(event -> {
            try {
                out.writeObject(new Message(user, target_uuid , MESSAGE_ACTION.CALL_REJECT ));
                out.flush();
                callFrame.dispose();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        btnAccept.addActionListener(event -> {
            try {
                out.writeObject(new Message(user, target_uuid , MESSAGE_ACTION.CALL_ACCEPT ));
                out.flush();
                initSpeakers();
                sendMicrophone(target_uuid);
                btnAccept.setVisible(false);
                btnReject.setVisible(false);
                btnEnd.setVisible(true);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        btnEnd.addActionListener(event -> {
            try {
                out.writeObject(new Message(user, target_uuid , MESSAGE_ACTION.CALL_END ));
                out.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            callFrame.dispose();
            speakers.close();
            stopSendMicrophone();
        }); 
    }

    public void sendMessage() {
        if (tfMessage.getText().isEmpty()) {
            showWarning("Message empty!");
            return;
        }

        if (tfMessage.getText().length() > 512) {
            showWarning("Message size limit reached. Limit 512!");
            return;
        }

        try {
            out.writeObject(new Message(tfMessage.getText(), new User(user.getName(), user.getUuid()), listOfChats.getSelectedValue().getId(), listOfChats.getSelectedValue().getRoomType()));
            out.flush();
            tfMessage.setText("");
            tfMessage.requestFocus();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void systemMessage(Message msg) {
        var users = getChatByUUID().getUsers();
        switch (msg.getMessage_action()) {
            case NEW_USER -> {
                //v sis
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getUuid().equals(msg.getUser().getUuid())) return;
                }
                users.add(users.size(), msg.getUser());
            }
            case DEL_USER -> {
                for (int i = 0; i < chats.size(); i++) {
                    users = chats.get(i).getUsers();
                    for (int j = 0; j < users.size(); j++) {
                        if (users.get(j).getUuid().equals(msg.getUser().getUuid())) {
                            users.remove(j);
                        }
                    }
                }

            }
            case RENAME_USER -> {
                for (int i = 0; i < chats.size(); i++) {
                    users = chats.get(i).getUsers();
                    for (int j = 0; j < users.size(); j++) {
                        if (users.get(j).getUuid().equals(msg.getUser().getUuid())) {
                            users.set(j, msg.getUser());
                        }
                    }

                    if (chats.get(i).getId().equals(msg.getUser().getUuid())) {
                        var lol = chats.get(i);
                        lol.setName(msg.getUser().getName());
                        chats.set(i, lol);
                    }
                }
            }
            case NEW_GROUP -> {
                try {
                    Group group = (Group) in.readObject();
                    chats.add(chats.size(), new ChatRoom(group.getId(), group.getUsers(), group.getName(), ROOM_TYPE.GROUP));
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            case RENAME_GROUP -> {
                getChatByUUID(msg.getReceiver()).setName(msg.getText());
                listOfChats.repaint();
                getChatByUUID(msg.getReceiver()).addMessage(new Message(msg.getUser().getName() + " переименовал группу!"));
            }
            case ADD_USER_TO_GROUP -> {

                var usersInGroup = getChatByUUID(msg.getReceiver()).getUsers();
                usersInGroup.add(usersInGroup.size(), msg.getUser());

            }
            case CALL_REQUEST -> {
                showCallFrame(msg.getUser(), 1);
            }
            case CALL_ACCEPT -> {
                initSpeakers();
                sendMicrophone(msg.getUser().getUuid());
                btnCalling.setVisible(false);
                btnEnd.setVisible(true);
            }
            case CALL_REJECT -> {
                callFrame.dispose();
            }
            case CALL_END -> {
                speakers.close();
                callFrame.dispose();
                stopSendMicrophone();
            }
            case CALL_DATA -> {


                try {
                    //while (true) {
                        int numBytesRead = 0;
                        byte[] data = new byte[81920];
                        BufferedInputStream inB = new BufferedInputStream(in);
                        while (numBytesRead != data.length)
                        numBytesRead += inB.read(data,numBytesRead,data.length - numBytesRead);

                        System.out.println("readed " + numBytesRead);
                        speakers.write(data, 0, numBytesRead);
                    //}
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void ProcessMessage(Message msg) {

        switch (msg.getRoom_type()) {
            case DIRECT -> {
                var id = (user.getUuid().equals(msg.getUser().getUuid())) ? msg.getReceiver() : msg.getUser().getUuid();

                var room = getChatByUUID(id);

                if (room == null) {
                    chats.add(chats.size(), new ChatRoom(msg.getUser().getUuid(), new ArrayList<>(Arrays.asList(msg.getUser(), user)), msg.getUser().getName(), ROOM_TYPE.DIRECT));
                    room = getChatByUUID(id);
                }
                room.addMessage(msg);
            }
            case GROUP -> {
                var room = getChatByUUID(msg.getReceiver());
                if (room == null) {
                    System.out.println("Something went wrong!");
                } else
                    room.addMessage(msg);
            }
        }

    }












     List<Sender> senderList = new ArrayList<>();
     MicrophoneReader mr;
     volatile Integer sendersCreated = 0;
     volatile Integer numBytesRead = 0;
     volatile Integer senderNotReady = 0;
     volatile byte[] data;
     static final Object monitor = new Object();
    UUID target_id = null;
    public void sendMicrophone(UUID target_id) {
        try {
            this.target_id = target_id;
            mr = new MicrophoneReader();
            mr.start();
            //while (true) {

                Sender sndr = new Sender();
                senderList.add(sndr);
                sndr.start();
            //}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initSpeakers(){
        try {
            AudioFormat format = new AudioFormat(48000.0f, 16, 2, true, false);
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            speakers.open(format);
            speakers.start();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stopSendMicrophone()
    {
        mr.setFinishFlag();
        for (Sender sndr: senderList) {
            sndr.setFinishFlag();
        }
    }

     class Sender extends Thread {
         OutputStream s;
        volatile boolean finishFlag= false;
        int position;
        int senderNumber;

        public Sender() {
            finishFlag = false;
            System.out.print("Sender started: #");
            senderNumber = ++sendersCreated;
            System.out.println(senderNumber);
        }

        public void setFinishFlag() {
            finishFlag = true;
        }

        public void run() {
            try {
                while (!finishFlag) {

                    synchronized (monitor) {
                        senderNotReady++;
                        monitor.wait();
                        out.writeObject(new Message(user, target_id ,MESSAGE_ACTION.CALL_DATA));
                        out.flush();
                        BufferedOutputStream outB = new BufferedOutputStream(out);
                        outB.write(data, 0, numBytesRead);
                        outB.flush();

                        senderNotReady--;
                    }
                    Thread.sleep(1);
                    System.out.print("Sender #");
                    System.out.print(senderNumber);
                    System.out.print(" ");
                    System.out.print(numBytesRead);
                    System.out.println(" bytes sent");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

     class MicrophoneReader extends Thread {
        volatile boolean finishFlag = false;

        AudioFormat format = new AudioFormat(48000.0f, 16, 2, true, false);
        int CHUNK_SIZE = 81920;
        TargetDataLine microphone;

        public MicrophoneReader() {
            try {
            finishFlag = false;
            microphone = AudioSystem.getTargetDataLine(format);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
            data = new byte[CHUNK_SIZE];
            microphone.start();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            System.out.println("Microphone reader started");
        }

        public void setFinishFlag() {
            finishFlag = true;
        }

        public void run() {
            try {
                while (!finishFlag) {
                    synchronized (monitor) {

                        if (senderNotReady.equals(sendersCreated)) {
                            monitor.notifyAll();
                            continue;
                        }
                        System.out.println("прочитано");
                        numBytesRead = microphone.read(data, 0, CHUNK_SIZE);

                    }
                    Thread.sleep(1);
                    System.out.print("Microphone reader: ");
                    System.out.print(numBytesRead);
                    System.out.println(" bytes read");

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }




}

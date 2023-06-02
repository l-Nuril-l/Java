import Models.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;
import java.util.Vector;

public class Listener implements Runnable {

    User user = null;
    Socket client = null;
    ObjectInputStream in = null;
    protected ObjectOutputStream out = null;
    Message msg = null;
    byte[] data = null;
    MessengerServer server;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Listener(Socket client, MessengerServer server) {
        this.client = client;
        this.server = server;
    }

    @Override
    public void run() {
        try {

            in = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
            out = new ObjectOutputStream(new BufferedOutputStream(client.getOutputStream()));
            server.AddClient(this);
            out.flush();
            user = new User((String)in.readObject(), UUID.randomUUID());
            out.writeObject(user.getUuid());


            var users = new DefaultListModel<User>();
            new Vector<>(server.clients.stream().map(Listener::getUser).toList()).forEach(x -> users.add(users.size(),x));
            out.writeObject(users);
            out.flush();
            server.ProcessMessage(new Message(user.getName() + " присоединился в чат!",user, MESSAGE_ACTION.NEW_USER));
            while ((msg = (Message) in.readObject()) != null) {
                System.out.println(msg.getMessage_action());
                byte[] bytes = new byte[32 * 1024];
                int count, total = 0;
                if(msg.getMessage_action() == null) {
                    if(msg.hasFile())
                    {
                        BufferedOutputStream outFB = new BufferedOutputStream(new FileOutputStream(msg.getFile()));

                        long length = in.readLong();

                        System.out.println(length);

                        long start = Calendar.getInstance().getTimeInMillis();
                        var inB = new BufferedInputStream(in);
                        while ((count = inB.read(bytes,0,bytes.length)) > -1) {
                            total += count;
                            outFB.write(bytes, 0, count);
                            if (total == length) break;
                        }
                        outFB.flush();
                        var res = (Calendar.getInstance().getTimeInMillis() - start);
                        System.out.println("Elapsed time in ms: " + res);
                        System.out.println("File size MB: " + length / 1024 / 1024);
                        outFB.close();

                        if ( msg.getFile().endsWith(".jpg")) {
                            BufferedImage bImage = ImageIO.read(new File(msg.getFile()));
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ImageIO.write(bImage, "jpg", baos);
                            new ImageFrame(baos.toByteArray());
                        }
                    }

                    server.ProcessMessage(msg);
                }
                else
                    switch (msg.getMessage_action()) {
                        case RENAME_USER -> {
                            var old_name = user.getName();
                            user.setName(msg.getUser().getName());
                            msg.setText(old_name + " меняет имя на " + user.getName());
                            server.ProcessMessage(msg);

                        }
                        case DOWNLOAD -> {
                            String full_path = (String) in.readObject();
                            if (msg.getFile() == null) continue;
                            File file = new File(msg.getFile());
                            if (!file.exists()) continue;

                            out.writeObject(msg);
                            out.writeLong(file.length());
                            out.writeObject(full_path);
                            out.flush();
                            BufferedInputStream inBF = new BufferedInputStream(new FileInputStream(file));
                            while ((count = inBF.read(bytes)) > -1) {
                                out.write(bytes, 0, count);
                            }
                            out.flush();

                        }
                        case NEW_GROUP -> {
                            server.groups.add((Group) in.readObject());
                            var r_out = server.getUserById(msg.getReceiver());
                            r_out.writeObject(msg);
                            r_out.writeObject(server.groups.get(server.groups.size() - 1));
                            r_out.flush();

                        }
                        case LEAVE_GROUP -> {
                            for (Group group : server.groups) {
                                if (group.getId().equals(msg.getReceiver())) {
                                    System.out.println(group.getUsers().remove(msg.getUser()) + " ALARM IF FALSE!!!");
                                    if (group.getUsers().size() < 1)
                                        server.groups.remove(group);
                                    break;
                                }
                            }

                        }
                        case RENAME_GROUP -> {
                            for (Group group : server.groups) {
                                if (group.getId().equals(msg.getReceiver())) {
                                    group.setName(msg.getText());
                                    for (User user : group.getUsers()) {
                                        var u_out = server.getUserById(user.getUuid());
                                        u_out.writeObject(msg);
                                        u_out.flush();
                                    }
                                    break;
                                }
                            }

                        }
                        case ADD_USER_TO_GROUP -> {
                            for (Group group : server.groups) {
                                if (group.getId().equals(msg.getReceiver())) {
                                    for (User user : group.getUsers()) {
                                        server.getUserById(user.getUuid()).writeObject(msg);
                                    }

                                    //Уведомить добавляемого
                                    group.getUsers().add(msg.getUser());
                                    var r_out = server.getUserById(msg.getUser().getUuid());
                                    msg.setMessage_action(MESSAGE_ACTION.NEW_GROUP);
                                    r_out.writeObject(msg);
                                    msg.setMessage_action(MESSAGE_ACTION.ADD_USER_TO_GROUP);
                                    r_out.writeObject(group);
                                    r_out.flush();
                                    break;
                                }
                            }
                        }
                        case CALL_REQUEST,CALL_END,CALL_ACCEPT,CALL_REJECT -> {
                            var r_out = server.getUserById(msg.getReceiver());
                            r_out.writeObject(msg);
                            r_out.flush();
                        }
                        case CALL_DATA -> {
                            var r_out = server.getUserById(msg.getReceiver());
                            BufferedInputStream inb = new BufferedInputStream(in);
                            byte[] data = new byte[81920];
                            int numBytesRead = 0;
                            while (numBytesRead != data.length)
                            {
                                System.out.println(numBytesRead);
                                numBytesRead += inb.read(data,numBytesRead,data.length - numBytesRead);
                            }

                            System.out.println(numBytesRead);
                            //if (numBytesRead == -1) return;
                            r_out.writeObject(msg);
                            r_out.write(data, 0 ,numBytesRead);
                            r_out.flush();
                        }
                        default -> server.ProcessMessage(msg);
                    }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            server.RemClient(this);
            server.ProcessMessage(new Message(  user.getName() + " покинул чат!",user, MESSAGE_ACTION.DEL_USER));
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}

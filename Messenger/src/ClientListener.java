import Models.MESSAGE_ACTION;
import Models.Message;

import javax.swing.*;
import java.io.*;
import java.util.UUID;

public class ClientListener implements Runnable {

    ObjectInputStream in = null;
    Message msg = null;
    MessengerClient client = null;


    public ClientListener(MessengerClient client, ObjectInputStream in) {
        this.in = in;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            while ((msg = (Message) in.readObject()) != null) {

                if (msg.isSystem()) {
                    client.systemMessage(msg);
                    if (msg.getMessage_action() == MESSAGE_ACTION.DOWNLOAD) {
                        var length = in.readLong();

                        byte[] bytes = new byte[32 * 1024];
                        int count, total = 0;

                        String fullName = (String) in.readObject();
                        BufferedOutputStream outFB = new BufferedOutputStream(new FileOutputStream(fullName));

                        var inB = new BufferedInputStream(in);
                        while ((count = inB.read(bytes, 0, bytes.length)) > -1) {
                            total += count;
                            outFB.write(bytes, 0, count);
                            if (total == length) break;
                        }
                        outFB.flush();
                        outFB.close();

                        JOptionPane.showMessageDialog(client,
                                "Файл '" + fullName +
                                        " ) сохранен");
                        break;
                    }
                }

                if (msg.getRoom_type() != null) {
                    switch (msg.getRoom_type()) {
                        case GENERAL: {
                            var chat = client.getChatByUUID().getMessages();
                            chat.add(chat.size(), msg);
                            while (chat.size() > 128)
                                chat.remove(0);
                            break;
                        }

                        case DIRECT: {
                        }
                        case GROUP: {
                            client.ProcessMessage(msg);
                            break;
                        }
                    }
                    client.scrollMsgJListToEnd();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

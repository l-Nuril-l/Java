import Models.Group;
import Models.MESSAGE_ACTION;
import Models.Message;
import Models.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

public class MessengerServer {
    ServerSocket listener = null;
    Socket client = null;
    int port = 8888;
    protected ArrayList<Listener> clients = new ArrayList<>();
    protected ArrayList<Group> groups = new ArrayList<>();

    public MessengerServer() {
        try {
            listener = new ServerSocket(port);
            while (true) {
                client = listener.accept();
                Thread t = new Thread(new Listener(client, this));
                t.setDaemon(true);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ProcessMessage(Message msg) {
        switch (msg.getRoom_type()) {
            case GENERAL -> {
                clients.forEach(x -> {
                    try {
                        x.out.writeObject(msg);
                        x.out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            case DIRECT -> {
                clients.forEach(x -> {
                    if (x.getUser().getUuid().equals(msg.getReceiver()) || x.getUser().getUuid().equals(msg.getUser().getUuid())) {
                        try {
                            x.out.writeObject(msg);
                            x.out.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            case GROUP -> {
                Group group = null;
                for (Group value : groups) {

                    if (msg.getReceiver().equals(value.getId())) {
                        group = value;
                        break;
                    }
                }
                if (group == null) return;
                for (Listener listener : clients) {
                    for (User user : group.getUsers()) {
                        if (listener.getUser().getUuid().equals(user.getUuid()))
                        {
                            try {
                                listener.out.writeObject(msg);
                                listener.out.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }



            }
        }
    }

    public ObjectOutputStream getUserById(UUID id)
    {
        for (Listener l : clients)
        {
            if (l.getUser().getUuid().equals(id))
                return l.out;
        }
        return null;
    }


    public void AddClient(Listener client) {
        clients.add(client);
    }

    public void RemClient(Listener client) {
        groups.forEach(x -> {
            var users = x.getUsers();
            for (int i = 0; i < users.size(); i++) {
                if(client.getUser().getUuid().equals(users.get(i).getUuid())) x.getUsers().remove(users.get(i));
            }
        });
        clients.remove(client);
    }
}

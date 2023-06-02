package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Group implements Serializable {
    public Group(UUID id, String name , ArrayList<User> users)  {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    UUID id;
    ArrayList<User> users = new ArrayList<>();
    String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

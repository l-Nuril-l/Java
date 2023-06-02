package Models;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    public User(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public User() {
    }

    private String name = "";
    private UUID uuid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return name;
    }
}

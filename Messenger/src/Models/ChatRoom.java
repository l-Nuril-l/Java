package Models;

import javax.swing.*;
import java.awt.font.TextHitInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.Vector;

public class ChatRoom {

    UUID id;
    DefaultListModel<User> users = new DefaultListModel<>();
    String name;
    DefaultListModel<Message> messages = new DefaultListModel<>();
    ROOM_TYPE room_type;

    public ChatRoom(UUID id, ArrayList<User> users, String name,ROOM_TYPE room_type) {
        this.id = id;
        this.users = new DefaultListModel<>();
        users.forEach(x -> this.users.add(this.users.size(), x));
        this.name = name;
        this.room_type = room_type;
    }

    public ChatRoom(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
        this.room_type = ROOM_TYPE.GENERAL;
    }

    public ChatRoom() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public DefaultListModel<User> getUsers() {
        return users;
    }

    public void setUsers(DefaultListModel<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DefaultListModel<Message> getMessages() {
        return messages;
    }

    public void setMessages(DefaultListModel<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message message) {
        messages.add(messages.size(), message);
    }

    public ROOM_TYPE getRoomType() {
        return room_type;
    }

    public void setRoomType(ROOM_TYPE room_type) {
        this.room_type = room_type;
    }

    @Override
    public String toString() {
        return name;
    }
}

package Models;

import javax.sound.midi.Receiver;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Message implements Serializable {

    //Full Message
    public Message(String text, String file, User user, UUID receiver, MESSAGE_ACTION message_action, ROOM_TYPE room_type) {
        this.text = text != null ? text : "";
        this.file = file != null ? file : "";
        this.user = user;
        this.receiver = receiver;
        this.message_action = message_action;
        this.room_type = room_type;
        this.hasFile = !file.isEmpty();
    }

    //Message with File and Text
    public Message(String text,String file, User user, UUID receiver, ROOM_TYPE room_type) {
        this(text,file,user,receiver,null,room_type);
    }

    //Message
    public Message(String text, User user, UUID receiver, ROOM_TYPE room_type) {
        this(text,"",user,receiver,null,room_type);
    }

    //Message
    //Rename Group
    public Message(String text, User user, UUID receiver, MESSAGE_ACTION message_action) {
        this(text,"",user,receiver,message_action,null);
    }

    //General Message
    public Message(String text, User user) {
        this(text,"",user,null,null,ROOM_TYPE.GENERAL);
    }

    //Data Message
    public Message(User user,UUID receiver) {
        this("","",user,receiver,MESSAGE_ACTION.SYSTEM,ROOM_TYPE.GENERAL);
    }


    //System message
    //Default Message;
    public Message(String text, UUID receiver, ROOM_TYPE room_type) {
        this(text,"",null,receiver, null ,room_type);
    }

    //System message with Action
    //User Notify
    public Message(String text, User user, MESSAGE_ACTION message_action) {
        this(text,"",user,null, message_action ,ROOM_TYPE.GENERAL);
    }

    //System UserAction
    public Message(User user, MESSAGE_ACTION message_action) {
        this("","",user,null, message_action ,ROOM_TYPE.GENERAL);
    }

    //System UserAction
    public Message(User user, UUID receiver, MESSAGE_ACTION message_action, ROOM_TYPE room_type) {
        this("","",user,receiver, message_action ,room_type);
    }

    //System UserAction
    //Create Group
    public Message(User user,UUID receiver, MESSAGE_ACTION message_action) {
        this("","",user,receiver, message_action ,null);
    }

    //ActionMessage
    public Message(String file, MESSAGE_ACTION message_action) {
        this("",file,null,null, message_action ,ROOM_TYPE.GENERAL);
    }

    //System message
    public Message(String text) {
        this(text,"",null,null, MESSAGE_ACTION.SYSTEM , ROOM_TYPE.GENERAL);
    }

    private String text;
    private String file;
    private User user;
    private UUID receiver;
    private MESSAGE_ACTION message_action;
    private ROOM_TYPE room_type;
    private Boolean hasFile;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        hasFile = !file.isEmpty();
        this.file = file;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getReceiver() {
        return receiver;
    }

    public void setReceiver(UUID receiver) {
        this.receiver = receiver;
    }

    public MESSAGE_ACTION getMessage_action() {
        return message_action;
    }

    public void setMessage_action(MESSAGE_ACTION message_action) {
        this.message_action = message_action;
    }

    public ROOM_TYPE getRoom_type() {
        return room_type;
    }

    public void setRoom_type(ROOM_TYPE room_type) {
        this.room_type = room_type;
    }

    public Boolean isSystem() {
        return message_action != null;
    }

    public Boolean hasFile() {
        return hasFile;
    }

    @Override
    public String toString() {

        if (message_action != null) return "System > " + text;

        if (!hasFile) {
            return user + ": " + text;
        }
        else {
            if (!text.isEmpty())
                return "Получен файл " + file + " от " + user.getName() + " с сообщением " + text;
            return "Получен файл " + file + " от " + user.getName();
        }
    }
}

package com.utm.egomessenger;

import com.google.firebase.database.IgnoreExtraProperties;
import java.io.Serializable;

@IgnoreExtraProperties
public class Chat implements Serializable {
    private String chatName = "EGO Chat";

    private Message message = new Message();

    public Chat() {}

    public Chat(String chatName, Message message) {
        this.chatName = chatName;
        this.message = message;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}

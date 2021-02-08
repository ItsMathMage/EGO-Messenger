package com.utm.egomessenger;

import com.google.firebase.database.IgnoreExtraProperties;
import java.io.Serializable;

@IgnoreExtraProperties
public class User implements Serializable {
    private String email = "";
    private String initials = "";
    private String phone = "";
    private String password = "";

    private Chat chat = new Chat();

    public User() {}

    public User(String email, String initials, String phone, String password, Chat chat) {
        this.email = email;
        this.initials = initials;
        this.phone = phone;
        this.password = password;
        this.chat = chat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}

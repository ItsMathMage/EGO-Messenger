package com.utm.egomessenger;

import com.google.firebase.database.IgnoreExtraProperties;
import java.io.Serializable;

@IgnoreExtraProperties
public class User implements Serializable {
    private String email = "";
    private String initials = "";
    private String phone = "";
    private String password = "";

    public User() {}

    public User(String email, String initials, String phone, String password) {
        this.email = email;
        this.initials = initials;
        this.phone = phone;
        this.password = password;
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
}

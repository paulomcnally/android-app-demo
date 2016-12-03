package com.chocoyo.labs.app.demo.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by polin on 12-03-16.
 */

@IgnoreExtraProperties
public class JuanModel {

    public String username;
    public String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

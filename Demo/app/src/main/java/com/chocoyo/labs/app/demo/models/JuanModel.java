package com.chocoyo.labs.app.demo.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by polin on 12-03-16.
 */

@IgnoreExtraProperties
public class JuanModel implements Serializable {

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

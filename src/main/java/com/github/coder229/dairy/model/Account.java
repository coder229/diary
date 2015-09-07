package com.github.coder229.dairy.model;

import org.springframework.data.annotation.Id;

/**
 * Created by scott on 9/5/2015.
 */
public class Account {
    @Id
    private String id;
    private String username;
    private String password;
    private boolean enabled;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

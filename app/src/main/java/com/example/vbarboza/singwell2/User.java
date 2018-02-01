package com.example.vbarboza.singwell2;

/**
 * Created by evaramirez on 1/25/18.
 */

public class User {
    private int id;
    private String username, email;

    public User(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public int getId() {

        return id;
    }

    public String getUsername() {

        return username;
    }

    public String getEmail() {

        return email;
    }

}

package com.example.firebase;

import java.io.Serializable;

public class User implements Serializable {
    public String name;
    public String password;
    public String date;
    public String gender;
    public String email;
    public String role;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String name, String password,String email,String date,String gender,String role) {
        this.name = name;
        this.password = password;
        this.date = date;
        this.gender = gender;
        this.email = email;
        this.role = role;
    }
}

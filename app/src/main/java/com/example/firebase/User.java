package com.example.firebase;

import java.io.Serializable;

public class User implements Serializable {
    public String name;
    public String password;
    public String date;
    public String gender;
    public String email;
    public String role;
    public String avatarUrl;
    public String nickname;
    public Boolean status;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String name,String nickname ,String password,String email,String date,String gender,String role,String avatarUrl,Boolean status) {
        this.name = name;
        this.password = password;
        this.date = date;
        this.gender = gender;
        this.email = email;
        this.role = role;
        this.avatarUrl = avatarUrl;
        this.nickname = nickname;
        this.status = status;
    }
}

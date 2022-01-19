package com.example.app.Model;

import java.util.ArrayList;

public class UserRegistration {

    public String name,email,phone,password;

    public UserRegistration() {

    }

    public UserRegistration(String name, String email, String phone,String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password=password;
    }

}

package com.example.vanessa.myapplication.Models;

import java.io.Serializable;

/**
 * Created by vanessa on 23/02/18.
 */

public class Contact implements Serializable {

    private String name;
    private String phone;

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

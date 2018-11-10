package com.example.vanessa.myapplication.Models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by vanessa on 17/02/18.
 */

public class Event implements Serializable{

    private int id;
    private String name;
    private String address;
    private String type;

    private GregorianCalendar date;

    public Event(int id, String name, String address, String type, GregorianCalendar date) {
        this.setId(id);
        this.setName(name);
        this.setAddress(address);
        this.setType(type);
        this.setDate(date);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

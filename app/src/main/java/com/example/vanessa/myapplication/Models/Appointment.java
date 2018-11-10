package com.example.vanessa.myapplication.Models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by vanessa on 17/02/18.
 */

public class Appointment implements Serializable {

    private int id;
    private String address;
    private String contact;
    private String info;
    private GregorianCalendar date;

    public Appointment(int id, String address, String contact, GregorianCalendar date) {
        this.setId(id);
        this.setAddress(address);
        this.setContact(contact);
        this.setDate(date);
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

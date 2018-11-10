package com.example.vanessa.myapplication;


import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Created by vanessa on 25/02/18.
 */

public class CalendarHelper {

    public static final SimpleDateFormat ISOFormatter = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
    public static final SimpleDateFormat viewDateFormatter = new java.text.SimpleDateFormat("dd-MM-yyyy");
    public static final SimpleDateFormat viewTimeFormatter = new java.text.SimpleDateFormat("HH:mm");

    private GregorianCalendar dateSelected;

    private static CalendarHelper instance = null;

    // private constructor restricted to this class itself
    private CalendarHelper()
    {
        dateSelected = null;
    }

    // static method to create instance of Singleton class
    public static CalendarHelper getInstance()
    {
        if (instance == null)
            instance = new CalendarHelper();

        return instance;
    }

    public GregorianCalendar getDateSelected() {
        return dateSelected;
    }

    public void setDateSelected(GregorianCalendar dateSelected) {
        this.dateSelected = dateSelected;
    }
}

package com.example.vanessa.myapplication.Models;

/**
 * Created by vanessa on 25/02/18.
 */

public class Reminder {
    private String time;
    private String remainingTime;
    private String reminderName;

    public Reminder(String time, String remainingTime, String reminderName) {
        this.time = time;
        this.remainingTime = remainingTime;
        this.reminderName = reminderName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }

    public String getReminderName() {
        return reminderName;
    }

    public void setReminderName(String reminderName) {
        this.reminderName = reminderName;
    }
}

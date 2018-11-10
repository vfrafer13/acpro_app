package com.example.vanessa.myapplication.Interfaces;

import com.example.vanessa.myapplication.Models.Appointment;
import com.example.vanessa.myapplication.Models.Contact;
import com.example.vanessa.myapplication.Models.Event;
import com.example.vanessa.myapplication.Models.Reminder;

/**
 * Created by vanessa on 22/02/18.
 */

public interface FragmentComunication {

    public void sendAppointment(Appointment appointment);

    public void sendEvent(Event event);

}

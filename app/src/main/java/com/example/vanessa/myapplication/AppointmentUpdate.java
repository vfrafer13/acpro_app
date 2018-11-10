package com.example.vanessa.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vanessa.myapplication.Models.Appointment;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AppointmentUpdate extends AppCompatActivity {

    DBHelper conn;
    Appointment appointment;
    int id;
    String contact, address, time;
    EditText updateTxtContact, updateTxtAddress, updateTxtTime;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conn = new DBHelper(getApplicationContext());
        setContentView(R.layout.activity_appointment_update);

        updateTxtContact= (EditText) findViewById(R.id.updateTxtContact);
        updateTxtAddress= (EditText) findViewById(R.id.updateTxtAddress);
        updateTxtTime = findViewById(R.id.editTxtEditAppDate);

        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            id= intent.getIntExtra("appointment_id", 0);
            contact = intent.getStringExtra("appointment_contact");
            address = intent.getStringExtra("appointment_address");
            time = intent.getStringExtra("appointment_time");
        }

        updateTxtAddress.setText(address);
        updateTxtContact.setText(contact);
        updateTxtTime.setText(time);

        toolbar = (Toolbar) findViewById(R.id.toolbar_appointment_update);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        AppointmentUpdate.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }
        setTitle("Cita");
    }

    public void onClick(View view) {
        updateAppointment();
    }

    public void updateAppointment() {
        String contactTxt = updateTxtContact.getText().toString();
        String addressTxt = updateTxtAddress.getText().toString();
        String timeTxt = updateTxtTime.getText().toString();

        GregorianCalendar gCalendar = new GregorianCalendar();
        GregorianCalendar hCalendar = CalendarHelper.getInstance().getDateSelected();

        try {
            Date date = CalendarHelper.viewTimeFormatter.parse(timeTxt);
            gCalendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        gCalendar.set(Calendar.YEAR, hCalendar.get(Calendar.YEAR));
        gCalendar.set(Calendar.MONTH, hCalendar.get(Calendar.MONTH));
        gCalendar.set(Calendar.DAY_OF_MONTH, hCalendar.get(Calendar.DAY_OF_MONTH));


        appointment = new Appointment(id, addressTxt, contactTxt
                , gCalendar);

        String formatted = CalendarHelper.ISOFormatter.format(gCalendar.getTime());
        SQLiteDatabase db=conn.getWritableDatabase();
        String[] parametros={"" + appointment.getId()};
        ContentValues values=new ContentValues();
        values.put(HelperContract.AppointmentEntry.COLUMN_CONTACT,appointment.getContact());
        values.put(HelperContract.AppointmentEntry.COLUMN_ADDRESS,appointment.getAddress());
        values.put(HelperContract.AppointmentEntry.COLUMN_DATE, formatted);

        db.update(HelperContract.AppointmentEntry.TABLE_NAME,values,HelperContract.AppointmentEntry._ID+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Ya se actualizó la cita",Toast.LENGTH_LONG).show();
        db.close();
        setResult(RESULT_OK, null);
        finish();
    }
}

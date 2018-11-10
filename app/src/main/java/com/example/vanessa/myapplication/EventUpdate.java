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

import com.example.vanessa.myapplication.Models.Event;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class EventUpdate extends AppCompatActivity {

    DBHelper conn;
    Event event;
    int id;
    String name, type, address, time;
    EditText updateTxtEventName, updateTxtEventType, updateTxtEventAddress, updateTxtEventTime;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conn = new DBHelper(getApplicationContext());
        setContentView(R.layout.activity_event_update);

        updateTxtEventName= (EditText) findViewById(R.id.updateTxtEventName);
        updateTxtEventType= (EditText) findViewById(R.id.updateTxtEventType);
        updateTxtEventAddress= (EditText) findViewById(R.id.updateTxtEventAddress);
        updateTxtEventTime = findViewById(R.id.editTxtEditEvDate);

        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            id= intent.getIntExtra("event_id", 0);
            name = intent.getStringExtra("event_name");
            address = intent.getStringExtra("event_address");
            type = intent.getStringExtra("event_type");
            time = intent.getStringExtra("event_time");
        }

        updateTxtEventAddress.setText(address);
        updateTxtEventName.setText(name);
        updateTxtEventType.setText(type);
        updateTxtEventTime.setText(time);

        toolbar = (Toolbar) findViewById(R.id.toolbar_event_update);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        EventUpdate.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }

        setTitle("Evento");
    }

    public void onClick(View view) {
        updateEvent();
    }

    public void updateEvent() {

        String eventName = updateTxtEventName.getText().toString();
        String eventAddress = updateTxtEventAddress.getText().toString();
        String eventType = updateTxtEventType.getText().toString();
        String eventTimeString = updateTxtEventTime.getText().toString();

        GregorianCalendar gCalendar = new GregorianCalendar();
        GregorianCalendar hCalendar = CalendarHelper.getInstance().getDateSelected();

        try {
            Date date = CalendarHelper.viewTimeFormatter.parse(eventTimeString);
            gCalendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        gCalendar.set(Calendar.YEAR, hCalendar.get(Calendar.YEAR));
        gCalendar.set(Calendar.MONTH, hCalendar.get(Calendar.MONTH));
        gCalendar.set(Calendar.DAY_OF_MONTH, hCalendar.get(Calendar.DAY_OF_MONTH));

        event = new Event(id, eventName, eventAddress, eventType, gCalendar);

        String formatted = CalendarHelper.ISOFormatter.format(gCalendar.getTime());

        SQLiteDatabase db=conn.getWritableDatabase();

        String[] parametros={"" + event.getId()};
        ContentValues values=new ContentValues();
        values.put(HelperContract.EventEntry.COLUMN_NAME,event.getName());
        values.put(HelperContract.EventEntry.COLUMN_ADDRESS,event.getAddress());
        values.put(HelperContract.EventEntry.COLUMN_TYPE,event.getType());
        values.put(HelperContract.EventEntry.COLUMN_DATE, formatted);

        db.update(HelperContract.EventEntry.TABLE_NAME,values,HelperContract.EventEntry._ID+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Ya se actualizó el evento",Toast.LENGTH_LONG).show();
        db.close();
        setResult(RESULT_OK, null);
        finish();
    }
}

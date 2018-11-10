package com.example.vanessa.myapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class EventRegistration extends AppCompatActivity {

    EditText editTxtName, editTxtAddress, editTxtType, editTextTime;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_registration);

        editTxtName= (EditText) findViewById(R.id.editTxtName);
        editTxtAddress= (EditText) findViewById(R.id.editTxtEventAddress);
        editTxtType= (EditText) findViewById(R.id.editTxtType);
        editTextTime = findViewById(R.id.editTxtAddEvDate);

        toolbar = findViewById(R.id.toolbar_event_registration);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        EventRegistration.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }

        setTitle("Evento");
    }

    public void onClick(View view) {
        newEvent();
    }

    private void newEvent() {
        DBHelper conn=new DBHelper(this);

        SQLiteDatabase db=conn.getWritableDatabase();

        String timeText = editTextTime.getText().toString();
        GregorianCalendar gCalendar = new GregorianCalendar();
        GregorianCalendar hCalendar = CalendarHelper.getInstance().getDateSelected();

        try {
            Date date = CalendarHelper.viewTimeFormatter.parse(timeText);
            gCalendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        gCalendar.set(Calendar.YEAR, hCalendar.get(Calendar.YEAR));
        gCalendar.set(Calendar.MONTH, hCalendar.get(Calendar.MONTH));
        gCalendar.set(Calendar.DAY_OF_MONTH, hCalendar.get(Calendar.DAY_OF_MONTH));

        String formatted = CalendarHelper.ISOFormatter.format(gCalendar.getTime());

        ContentValues values=new ContentValues();
        values.put(HelperContract.EventEntry.COLUMN_ADDRESS,editTxtAddress.getText().toString());
        values.put(HelperContract.EventEntry.COLUMN_NAME,editTxtName.getText().toString());
        values.put(HelperContract.EventEntry.COLUMN_TYPE,editTxtType.getText().toString());
        values.put(HelperContract.EventEntry.COLUMN_DATE, formatted);

        Long idResultante=db.insert(HelperContract.EventEntry.TABLE_NAME, HelperContract.EventEntry._ID,values);


        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante,Toast.LENGTH_SHORT).show();

        db.close();
        setResult(RESULT_OK, null);
        finish();
    }
}

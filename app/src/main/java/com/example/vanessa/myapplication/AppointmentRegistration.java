package com.example.vanessa.myapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AppointmentRegistration extends AppCompatActivity {

    EditText editTxtContact, editTxtAddress, editTxtTime;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_registration);

        toolbar = (Toolbar) findViewById(R.id.toolbar_appointment_registration);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        AppointmentRegistration.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }

        editTxtContact= (EditText) findViewById(R.id.editTxtContact);
        editTxtAddress= (EditText) findViewById(R.id.editTxtAddress);
        editTxtTime = findViewById(R.id.editTxtAddAppDate);

        setTitle("Cita");
    }

    public void onClick(View view) {
        newAppointment();
    }

    private void newAppointment() {
        DBHelper conn=new DBHelper(this);

        SQLiteDatabase db=conn.getWritableDatabase();

        String timeText = editTxtTime.getText().toString();
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
        values.put(HelperContract.AppointmentEntry.COLUMN_ADDRESS,editTxtAddress.getText().toString());
        values.put(HelperContract.AppointmentEntry.COLUMN_CONTACT,editTxtContact.getText().toString());
        values.put(HelperContract.AppointmentEntry.COLUMN_DATE, formatted);

        Long idResultante=db.insert(HelperContract.AppointmentEntry.TABLE_NAME, HelperContract.AppointmentEntry._ID,values);

        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante,Toast.LENGTH_SHORT).show();

        db.close();
        setResult(RESULT_OK, null);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

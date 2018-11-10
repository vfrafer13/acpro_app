package com.example.vanessa.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vanessa on 17/02/18.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "acpro.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + HelperContract.EventEntry.TABLE_NAME + " (" +
                HelperContract.EventEntry._ID + " INTEGER PRIMARY KEY," +
                HelperContract.EventEntry.COLUMN_NAME + " TEXT, " +
                HelperContract.EventEntry.COLUMN_TYPE + " TEXT, " +
                HelperContract.EventEntry.COLUMN_ADDRESS + " TEXT, " +
                HelperContract.EventEntry.COLUMN_DATE + " DATETIME)"
        );

        db.execSQL("CREATE TABLE " + HelperContract.AppointmentEntry.TABLE_NAME + " (" +
                HelperContract.AppointmentEntry._ID + " INTEGER PRIMARY KEY," +
                HelperContract.AppointmentEntry.COLUMN_CONTACT + " TEXT, " +
                HelperContract.AppointmentEntry.COLUMN_ADDRESS + " TEXT, " +
                HelperContract.AppointmentEntry.COLUMN_DATE + " DATETIME )");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HelperContract.EventEntry.TABLE_NAME);
        onCreate(db);
    }
}

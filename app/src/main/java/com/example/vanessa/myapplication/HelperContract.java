package com.example.vanessa.myapplication;

import android.provider.BaseColumns;

/**
 * Created by vanessa on 17/02/18.
 */

public final class HelperContract {

    private HelperContract() {}

    public static class EventEntry implements BaseColumns {
        public static final String TABLE_NAME = "events";

        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_ADDRESS = "address";
    }

    public static class AppointmentEntry implements BaseColumns {
        public static final String TABLE_NAME = "appointments";

        public static final String COLUMN_CONTACT = "contact";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_ADDRESS = "address";
    }
}
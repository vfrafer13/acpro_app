package com.example.vanessa.myapplication.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vanessa.myapplication.Adapters.MyReminderRecyclerViewAdapter;
import com.example.vanessa.myapplication.DBHelper;
import com.example.vanessa.myapplication.HelperContract;
import com.example.vanessa.myapplication.Models.Reminder;
import com.example.vanessa.myapplication.R;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class ReminderFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    ArrayList<Reminder> reminders;
    Activity activity;
    RecyclerView recyclerView;

    DBHelper conn;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReminderFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ReminderFragment newInstance(int columnCount) {
        ReminderFragment fragment = new ReminderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminder_list, container, false);

        conn=new DBHelper(getContext());
        reminders = new ArrayList<>();

        recyclerView= view.findViewById(R.id.reminder_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        findRemindersDB();

        MyReminderRecyclerViewAdapter adapter=new MyReminderRecyclerViewAdapter(reminders);
        recyclerView.setAdapter(adapter);

        getActivity().setTitle("Recordatorios");

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void findRemindersDB () {
        SQLiteDatabase db=conn.getReadableDatabase();

        Reminder reminder = null;

        Cursor cursor=db.rawQuery("SELECT * FROM "+ HelperContract.EventEntry.TABLE_NAME,null);

        while (cursor.moveToNext()){
            //int id =cursor.getInt(0);
            String name =cursor.getString(1);
            //Date date = cursor.getString(4);
            reminder=new Reminder("6:00pm", "12 horas", name);

            reminders.add(reminder);
        }
    }

}

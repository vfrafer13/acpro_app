package com.example.vanessa.myapplication.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vanessa.myapplication.Adapters.MyAppointmentRecyclerViewAdapter;
import com.example.vanessa.myapplication.AppointmentRegistration;
import com.example.vanessa.myapplication.CalendarHelper;
import com.example.vanessa.myapplication.DBHelper;
import com.example.vanessa.myapplication.HelperContract;
import com.example.vanessa.myapplication.Interfaces.FragmentComunication;
import com.example.vanessa.myapplication.MainActivity;
import com.example.vanessa.myapplication.Models.Appointment;
import com.example.vanessa.myapplication.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static android.app.Activity.RESULT_OK;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class AppointmentFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnFragmentInteractionListener mListener;
    ArrayList<Appointment> appointments;
    Activity activity;
    RecyclerView recyclerView;
    FragmentComunication fragmentComunication;
    Intent intent;
    DBHelper conn;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AppointmentFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AppointmentFragment newInstance(int columnCount) {
        AppointmentFragment fragment = new AppointmentFragment();
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
        View view = inflater.inflate(R.layout.fragment_appointment_list, container, false);
        conn=new DBHelper(getContext());
        appointments = new ArrayList<>();
        recyclerView= (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        findAppointmentsDB();

        MyAppointmentRecyclerViewAdapter adapter=new MyAppointmentRecyclerViewAdapter(appointments);
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Seleccion: "+
                        appointments.get(recyclerView.
                                getChildAdapterPosition(view)).getContact() ,Toast.LENGTH_SHORT).show();

                fragmentComunication.sendAppointment(appointments.get(recyclerView.getChildAdapterPosition(view)));
            }
        });

        FloatingActionButton newAppointmentBtn = view.findViewById(R.id.floatingAddAppointment);

        intent = new Intent(getActivity(), AppointmentRegistration.class);

        getActivity().setTitle("Citas");

        newAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(intent, 1);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        if(resultCode==RESULT_OK){
            fragmentManager = getActivity().getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            AppointmentFragment appointmentFragment = new AppointmentFragment();
            fragmentTransaction.replace(R.id.fragment, appointmentFragment);
            fragmentTransaction.commit();
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof Activity){
            this.activity= (Activity) context;
            fragmentComunication= (FragmentComunication) this.activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void findAppointmentsDB() {
        SQLiteDatabase db=conn.getReadableDatabase();

        GregorianCalendar hCalendar = CalendarHelper.getInstance().getDateSelected();
        GregorianCalendar nextDayCalendar= new GregorianCalendar();
        nextDayCalendar.setTime(hCalendar.getTime());
        nextDayCalendar.add(Calendar.DAY_OF_MONTH, 1);
        nextDayCalendar.add(Calendar.SECOND, -1);

        String dateStringSql = CalendarHelper.ISOFormatter.format(hCalendar.getTime());
        String nextDayString = CalendarHelper.ISOFormatter.format(nextDayCalendar.getTime());

        Appointment appointment = null;

        Cursor cursor=db.rawQuery("SELECT * FROM "+ HelperContract.AppointmentEntry.TABLE_NAME
                + " WHERE " + HelperContract.AppointmentEntry.COLUMN_DATE
                + " BETWEEN '" + dateStringSql + "' AND '" + nextDayString + "'",null);

        while (cursor.moveToNext()){
            int id =cursor.getInt(0);
            String contact =cursor.getString(1);
            String address = cursor.getString(2);
            String dateString = cursor.getString(3);

            GregorianCalendar gCalendar = null;
            try {
                Date date = CalendarHelper.ISOFormatter.parse(dateString);

                gCalendar = new GregorianCalendar();
                gCalendar.setTime(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            appointment=new Appointment(id, address, contact, gCalendar);

            appointments.add(appointment);
        }
    }
}

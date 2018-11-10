package com.example.vanessa.myapplication.Fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vanessa.myapplication.AppointmentRegistration;
import com.example.vanessa.myapplication.AppointmentUpdate;
import com.example.vanessa.myapplication.CalendarHelper;
import com.example.vanessa.myapplication.DBHelper;
import com.example.vanessa.myapplication.HelperContract;
import com.example.vanessa.myapplication.Models.Appointment;
import com.example.vanessa.myapplication.Models.Event;
import com.example.vanessa.myapplication.R;

import java.util.GregorianCalendar;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AppointmentDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AppointmentDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentDetailFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView desc;
    DBHelper conn;
    Appointment appointment;
    Button btnAppDelete;
    Button btnAppEdit;
    TextView nameAppointment;

    private OnFragmentInteractionListener mListener;

    public AppointmentDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppointmentDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppointmentDetailFragment newInstance(String param1, String param2) {
        AppointmentDetailFragment fragment = new AppointmentDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointment_detail, container, false);
        desc= (TextView) view.findViewById(R.id.txt_infoAppointment);
        conn = new DBHelper(getContext());
        btnAppDelete = view.findViewById(R.id.btnAppDelete);
        btnAppEdit = view.findViewById(R.id.btnAppEdit);
        nameAppointment = view.findViewById(R.id.txtNameAppointment);

        Bundle appObject = getArguments();
        appointment=null;
        if (appObject != null) {
            appointment= (Appointment) appObject.getSerializable("app_object");

            setInfo(appointment);

        }

        btnAppEdit.setOnClickListener(this);
        btnAppDelete.setOnClickListener(this);

        getActivity().setTitle("Detalle de Citas");

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void setInfo(Appointment appointment) {
        GregorianCalendar calendar = appointment.getDate();
        String dateString = CalendarHelper.viewDateFormatter.format(calendar.getTime());
        String timeString = CalendarHelper.viewTimeFormatter.format(calendar.getTime());

        String info = dateString
                + "\n"
                + timeString + " hrs"
                + "\n"
                + appointment.getAddress();

        desc.setText(info);
        nameAppointment.setText(appointment.getContact());
    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnAppEdit: updateAppointment();
                break;
            case R.id.btnAppDelete: deleteAppointment();
                break;
        }

    }

    public void updateAppointment() {
        Intent intent = new Intent(getActivity(), AppointmentUpdate.class);
        intent.putExtra("appointment_id", appointment.getId());
        intent.putExtra("appointment_contact", appointment.getContact());
        intent.putExtra("appointment_address", appointment.getAddress());
        GregorianCalendar gCalendar = appointment.getDate();
        String time = CalendarHelper.viewTimeFormatter.format(gCalendar.getTime());
        intent.putExtra("appointment_time", time);
        startActivityForResult(intent, 1);

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

    public void deleteAppointment() {
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros= {("" + appointment.getId())};

        db.delete(HelperContract.AppointmentEntry.TABLE_NAME,HelperContract.AppointmentEntry._ID+"=?",parametros);
        Toast.makeText(getContext(),"Ya se Eliminó la cita",Toast.LENGTH_LONG).show();

        getFragmentManager().popBackStack();
        db.close();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

package com.example.vanessa.myapplication.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vanessa.myapplication.CalendarHelper;
import com.example.vanessa.myapplication.Models.Appointment;
import com.example.vanessa.myapplication.R;

import org.w3c.dom.Text;

import java.util.GregorianCalendar;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Appointment} and makes a call to the
 * specified {@link }.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyAppointmentRecyclerViewAdapter extends RecyclerView.Adapter<MyAppointmentRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private final List<Appointment> mValues;
    private View.OnClickListener listener;

    public MyAppointmentRecyclerViewAdapter(List<Appointment> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_appointment_item, parent, false);

        RecyclerView.LayoutParams layParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layParams);

        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        GregorianCalendar calendar = mValues.get(position).getDate();
        String dateString = CalendarHelper.viewDateFormatter.format(calendar.getTime());
        String timeString = CalendarHelper.viewTimeFormatter.format(calendar.getTime());

        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getContact());
        holder.mContentView.setText(timeString);
        holder.mDate.setText(dateString);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mDate;
        public Appointment mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mDate = view.findViewById(R.id.txtDate);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}

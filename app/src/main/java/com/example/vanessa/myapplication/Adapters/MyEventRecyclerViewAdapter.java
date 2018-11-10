package com.example.vanessa.myapplication.Adapters;

import android.icu.util.Calendar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vanessa.myapplication.CalendarHelper;
import com.example.vanessa.myapplication.Models.Event;
import com.example.vanessa.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class MyEventRecyclerViewAdapter extends RecyclerView.Adapter<MyEventRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private final List<Event> mValues;
    private View.OnClickListener listener;

    public MyEventRecyclerViewAdapter(List<Event> items) {
        mValues = items;
    }

    @Override
    public MyEventRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_event_item, parent, false);

        RecyclerView.LayoutParams layParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layParams);

        view.setOnClickListener(this);

        return new MyEventRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyEventRecyclerViewAdapter.ViewHolder holder, int position) {
        GregorianCalendar calendar = mValues.get(position).getDate();
        String dateString = CalendarHelper.viewDateFormatter.format(calendar.getTime());
        String timeString = CalendarHelper.viewTimeFormatter.format(calendar.getTime());

        holder.mItem = mValues.get(position);
        holder.mDate.setText(dateString);
        holder.mIdView.setText(mValues.get(position).getName());
        holder.mContentView.setText(timeString);
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
        public Event mItem;

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

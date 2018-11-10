package com.example.vanessa.myapplication.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vanessa.myapplication.Models.Reminder;
import com.example.vanessa.myapplication.R;

import java.util.List;

/**
 *
 * TODO: Replace the implementation with code for your data type.
 */
public class MyReminderRecyclerViewAdapter extends RecyclerView.Adapter<MyReminderRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private final List<Reminder> mValues;
    private View.OnClickListener listener;

    public MyReminderRecyclerViewAdapter(List<Reminder> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_reminder_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getReminderName() + ", "
                + mValues.get(position).getTime());
        holder.mContentView.setText("(En " + mValues.get(position).getRemainingTime() + ")");

    }

    @Override
    public int getItemCount() {
        return mValues.size();
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
        public Reminder mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}

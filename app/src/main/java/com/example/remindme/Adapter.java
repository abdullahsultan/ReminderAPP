package com.example.remindme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    ArrayList<ReminderItems> data;
    public Adapter(Context context, ArrayList<ReminderItems> data)
    {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reminder_items,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReminderItems reminderItems = this.data.get(position);
        holder.title.setText(reminderItems.title);
        String temp = Integer.toString(reminderItems.time_hours)+":" + Integer.toString(reminderItems.time_minutes)+":"+ reminderItems.time_AM_PM;
        holder.time.setText(temp);
        temp = Integer.toString(reminderItems.date_day)+"/"+Integer.toString(reminderItems.date_month)+"/"+Integer.toString(reminderItems.date_year);
        holder.date.setText(temp);
    }

    @Override
    public int getItemCount() {
            return this.data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView time;
        TextView date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.reminder_name);
            time = itemView.findViewById(R.id.reminder_time);
            date= itemView.findViewById(R.id.reminder_date);
        }
    }
}

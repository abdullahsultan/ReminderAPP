package com.example.remindme;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    public Context context;
    public static int number;
    public String time;
    public  String date;
    public String mainTitle;
    ArrayList<ReminderItems> data;
    MainActivity activity;
    public Adapter(Context context, ArrayList<ReminderItems> data, MainActivity activity)
    {
        this.context = context;
        this.data = data;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reminder_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        number = position;

        ReminderItems reminderItems = this.data.get(position);
        holder.title.setText(reminderItems.title);
        mainTitle = reminderItems.title;

        ///////////////////////////////////////////////////////// Setting Date & Time to UI /////////////////////////////////////////
        time = reminderItems.time_hours +":" + reminderItems.time_minutes +":"+ reminderItems.time_AM_PM;
        holder.time.setText(time);
        date = reminderItems.date_day +"/"+ (reminderItems.date_month + 1) +"/"+ reminderItems.date_year;
        holder.date.setText(date);


        //////////////////////////////////////////////Delete Reminder/////////////////////////////////////////
        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(position);
                if(data.size() == 0)
                {
                    activity.showOnEmpty();
                }

                notifyItemRemoved(position);
                notifyItemRangeChanged(position, data.size());
                cancelAlarm();
            }
        });

        holder.button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    edit();
            }
        });


    }


    @Override
    public int getItemCount() {
            return this.data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView time;
        TextView date;
        ImageButton button_delete;
        ImageButton button_edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.reminder_name);
            time = itemView.findViewById(R.id.reminder_time);
            date= itemView.findViewById(R.id.reminder_date);
            button_delete = itemView.findViewById(R.id.button_delete);
            button_edit = itemView.findViewById(R.id.ImageButton_Edit);
        }
    }


    public void cancelAlarm()
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, number, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    public void edit(){
        Intent intent = new Intent(context,MainActivity2.class);
        intent.putExtra("title",mainTitle);
        intent.putExtra("at",number);
        intent.putExtra("IsEdit",true);
        intent.putExtra("time",time);
        intent.putExtra("date",date);
        ((Activity)  context).startActivityForResult(intent,1);
    }

    public void add(ReminderItems r)
    {
        data.add(r);
        number = data.size() - 1;
        notifyDataSetChanged();

    }

    public  void addEdit(int at , ReminderItems reminderItems)
    {
        data.set(at,reminderItems);
        notifyDataSetChanged();
    }
}

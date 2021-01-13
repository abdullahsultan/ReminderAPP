package com.example.remindme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    public Context context;
    public int number;
    public String time;
    public  String date;
    public String mainTitle;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        number = position;
        ReminderItems reminderItems = this.data.get(position);
        holder.title.setText(reminderItems.title);
        mainTitle = reminderItems.title;

        ///////////////////////////////////////////////////////// Setting Date & Time to UI /////////////////////////////////////////
        time = Integer.toString(reminderItems.time_hours)+":" + Integer.toString(reminderItems.time_minutes)+":"+ reminderItems.time_AM_PM;
        holder.time.setText(time);
        date = Integer.toString(reminderItems.date_day)+"/"+Integer.toString(reminderItems.date_month+1)+"/"+Integer.toString(reminderItems.date_year);
        holder.date.setText(date);


        Calendar myAlarmDate = Calendar.getInstance();
        myAlarmDate.setTimeInMillis(System.currentTimeMillis());
        myAlarmDate.set(reminderItems.date_year, reminderItems.date_month, reminderItems.date_day, reminderItems.time_hours, reminderItems.time_minutes, 0);
        Log.i("DATETESTING",Integer.toString(reminderItems.date_year)+ "/" + Integer.toString(reminderItems.date_month)+"/" +Integer.toString(reminderItems.date_day)
        + "|||||" + Integer.toString(reminderItems.time_hours) + ":" + Integer.toString(reminderItems.time_minutes)
        );
        startAlarm(myAlarmDate);

        //////////////////////////////////////////////Delete Reminder/////////////////////////////////////////
        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(position);
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

    public void startAlarm(Calendar c)
    {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, number, intent, PendingIntent.FLAG_ONE_SHOT);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        Log.i("LALAHU",Long.toString(c.getTimeInMillis()));
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
        context.startActivity(intent);
    }
}

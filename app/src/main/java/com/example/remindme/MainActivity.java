package com.example.remindme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public  ArrayList<ReminderItems> reminderItemsArrayList = new ArrayList<>();
    public RecyclerView recyclerView;
    public Adapter adapter;
    public LinearLayoutManager layoutManager;
    public LinearLayout linearLayoutEmpty;
    public ReminderItems reminderItems;
    public Calendar myAlarmDate;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                // Get String data from Intent
                reminderItems =  data.getParcelableExtra("TheData");
                boolean isEdit = data.getBooleanExtra("IsEdit",false);

                if(isEdit)
                {
                        cancelPreviousReminder();
                        int at = data.getIntExtra("at",reminderItemsArrayList.size());
                        adapter.addEdit(at,reminderItems);
                        //reminderItemsArrayList.set(at,reminderItems);
                        myAlarmDate = Calendar.getInstance();
                        myAlarmDate.setTimeInMillis(System.currentTimeMillis());
                        myAlarmDate.set(reminderItems.date_year, reminderItems.date_month, reminderItems.date_day, reminderItems.time_hours, reminderItems.time_minutes, 0);
                }
                else
                {
                   // reminderItemsArrayList.add(reminderItems);
                    linearLayoutEmpty.setVisibility(View.GONE);
                    adapter.add(reminderItems);
                    myAlarmDate = Calendar.getInstance();
                    myAlarmDate.setTimeInMillis(System.currentTimeMillis());
                    myAlarmDate.set(reminderItems.date_year, reminderItems.date_month, reminderItems.date_day, reminderItems.time_hours, reminderItems.time_minutes, 0);
                    setAlarm(myAlarmDate);
                    Log.i("DATETESTING",Integer.toString(reminderItems.date_year)+ "/" + Integer.toString(reminderItems.date_month)+"/" +Integer.toString(reminderItems.date_day)
                            + "|||||" + Integer.toString(reminderItems.time_hours) + ":" + Integer.toString(reminderItems.time_minutes)
                    );
                }
                //adapter.notifyDataSetChanged();
                setAlarm(myAlarmDate);
            }
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ////////////////////////////////////Getting IDS///////////////////////////////////
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutEmpty = findViewById(R.id.layoutReminder);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);

        adapter = new Adapter(this,reminderItemsArrayList, this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivityForResult(intent,1);
            }
        });

    }


    public void setAlarm(Calendar c)
    {

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("title",reminderItems.title);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, Adapter.number, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }
    public void cancelPreviousReminder() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, Adapter.number, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    public  void showOnEmpty()
    {
        linearLayoutEmpty.setVisibility(View.VISIBLE);
    }

    public  void hideOnFill()
    {
        linearLayoutEmpty.setVisibility(View.GONE);
    }

}
package com.example.remindme;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity  {

    LinearLayout mainLinearLayout;
    EditText editText_title;
    TextView textView_time;
    Button button_Time;
    TextView textView_date;
    Button button_date;
    LinearLayout linearLayout_Time;
    TimePicker timePicker;
    Button button_timePicker;
    LinearLayout linearLayout_Date;
    DatePicker datePicker;
    Button button_datePicker;
    ImageButton imageButton_Cancel;
    ImageButton imageButton_OK;

    public boolean isEdit=false;
    public int at=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        GetIds();


        Intent intent = getIntent();
        isEdit = intent.getBooleanExtra("IsEdit",false);
        if(isEdit)
        {
            textView_time.setText(intent.getStringExtra("time"));
            textView_date.setText( intent.getStringExtra("date"));
            editText_title.setText(intent.getStringExtra("title"));
            at = intent.getIntExtra("at",0);

        }


        button_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide_Background();
                show_time();
            }
        });

        button_timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide_time();
                show_Background();
                set_time();
            }
        });

        button_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide_Background();
                show_date();
            }
        });


        button_datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide_date();
                show_Background();
                set_date();
            }
        });


        imageButton_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        /////////////////////////////OK/////////////////////////////

        imageButton_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ReminderItems reminderItems = get_return_data();
                Intent output = new Intent();
                output.putExtra("TheData", reminderItems);
                output.putExtra("IsEdit",isEdit);
                output.putExtra("at",at);
                setResult(RESULT_OK, output);
                finish();
            }
        });
    }

    public void GetIds(){
        /////////////////////////////Getting IDS////////////////////////////////////
        mainLinearLayout = findViewById(R.id.main_linear_layout);
        editText_title = findViewById(R.id.title);
        textView_time = findViewById(R.id.TextView_time);
        button_Time = findViewById(R.id.Button_time);
        textView_date = findViewById(R.id.TextView_Date);
        button_date = findViewById(R.id.Button_Date);
        linearLayout_Time = findViewById(R.id.LinearLayout_Time);
        timePicker = findViewById(R.id.TimePicker);
        timePicker.setIs24HourView(false);
        button_timePicker = findViewById(R.id.Button_TimeOK);
        linearLayout_Date = findViewById(R.id.LinearLayout_Date);
        datePicker = findViewById(R.id.DatePicker);
        button_datePicker = findViewById(R.id.Button_DateOK);
        imageButton_Cancel = findViewById(R.id.ImageButton_Cancel);
        imageButton_OK = findViewById(R.id.ImageButton_OK);
    }
    public void hide_Background()
    {
        mainLinearLayout.setVisibility(View.GONE);
        imageButton_OK.setVisibility(View.GONE);
        imageButton_Cancel.setVisibility(View.GONE);
    }

    public void show_Background()
    {
        mainLinearLayout.setVisibility(View.VISIBLE);
        imageButton_OK.setVisibility(View.VISIBLE);
        imageButton_Cancel.setVisibility(View.VISIBLE);
    }

    public void hide_time()
    {
        linearLayout_Time.setVisibility(View.GONE);
    }

    public void show_time()
    {
        linearLayout_Time.setVisibility(View.VISIBLE);
    }

    public void hide_date()
    {
        linearLayout_Date.setVisibility(View.GONE);
    }

    public void show_date()
    {
        linearLayout_Date.setVisibility(View.VISIBLE);
    }

    public void set_time()
    {
        String AM_PM;
        if(timePicker.getHour() < 12) {
            AM_PM = "AM";
        } else {
            AM_PM = "PM";
        }

        String time = timePicker.getHour() + ":" + timePicker.getMinute();

        try {
            String _24HourTime = time;
            @SuppressLint("SimpleDateFormat") SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm");
            Date _24HourDt = _24HourSDF.parse(_24HourTime);
            assert _24HourDt != null;
            time = _12HourSDF.format(_24HourDt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        time = time + "  " + AM_PM;
        textView_time.setText(time);
    }

    public void set_date(){
        String date = datePicker.getDayOfMonth() + "/" + datePicker.getMonth()+1 + "/" +datePicker.getYear();
        textView_date.setText(date);
    }

    public ReminderItems get_return_data()
    {
        ReminderItems reminderItems = new ReminderItems();
        reminderItems.title = editText_title.getText().toString();

        reminderItems.time_minutes = timePicker.getMinute();
        reminderItems.time_hours = timePicker.getHour();
        if(timePicker.getHour() < 12) {
            reminderItems.time_AM_PM = "AM";
        } else {
            reminderItems.time_AM_PM = "PM";
        }

        reminderItems.date_day = datePicker.getDayOfMonth();
        reminderItems.date_month = datePicker.getMonth();
        reminderItems.date_year = datePicker.getYear();

        return  reminderItems;
    }

}
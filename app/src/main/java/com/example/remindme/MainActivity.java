package com.example.remindme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                if(reminderItemsArrayList.isEmpty())
                    linearLayoutEmpty.setVisibility(View.VISIBLE);
                else
                    linearLayoutEmpty.setVisibility(View.GONE);

                // Get String data from Intent
                ReminderItems reminderItems;
                reminderItems =  data.getParcelableExtra("TheData");
                reminderItemsArrayList.add(reminderItems);
                adapter = new Adapter(this,reminderItemsArrayList);
                layoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
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
        registerReceiver(new AlertReceiver(),new IntentFilter());



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivityForResult(intent,1);
            }
        });

    }

}
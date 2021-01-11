package com.example.remindme;

public class ReminderItems {

    String title;

    int time_hours;
    int time_minutes;
    String time_AM_PM;

    int date_day;
    int date_month;
    int date_year;


    public void set_title(String title)
    {
        this.title =title;
    }

    public void set_time(int time_hours, int time_minutes,String time_AM_PM){
        this.time_hours = time_hours;
        this.time_minutes = time_minutes;
        this.time_AM_PM = time_AM_PM;
    }

    public void set_date(int date_day,int date_month, int date_year){
        this.date_day = date_day;
        this.date_month = date_month;
        this.date_year = date_year;
    }

}

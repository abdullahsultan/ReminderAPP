package com.example.remindme;

import android.os.Parcel;
import android.os.Parcelable;

public class ReminderItems implements Parcelable {

    String title;

    int time_hours;
    int time_minutes;
    String time_AM_PM;

    int date_day;
    int date_month;
    int date_year;


    protected ReminderItems(Parcel in) {
        title = in.readString();
        time_hours = in.readInt();
        time_minutes = in.readInt();
        time_AM_PM = in.readString();
        date_day = in.readInt();
        date_month = in.readInt();
        date_year = in.readInt();
    }

    public static final Creator<ReminderItems> CREATOR = new Creator<ReminderItems>() {
        @Override
        public ReminderItems createFromParcel(Parcel in) {
            return new ReminderItems(in);
        }

        @Override
        public ReminderItems[] newArray(int size) {
            return new ReminderItems[size];
        }
    };

    public ReminderItems() {

    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(time_hours);
        dest.writeInt(time_minutes);
        dest.writeString(time_AM_PM);
        dest.writeInt(date_day);
        dest.writeInt(date_month);
        dest.writeInt(date_year);
    }
}

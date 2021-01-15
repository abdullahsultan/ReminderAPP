package com.example.remindme;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Vibrator;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlertReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

        String title = intent.getStringExtra("title");

        MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_NOTIFICATION_URI);
        mediaPlayer.start();

        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(900);


        NotificationChannel channel = new NotificationChannel(
                "HEHE",
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.createNotificationChannel(channel);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "HEHE")
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("Don't forget")
                .setContentText("")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(title))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


// notificationId is a unique int for each notification that you must define
        notificationManager.notify(Adapter.number, builder.build());


       /* NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());*/
    }

}

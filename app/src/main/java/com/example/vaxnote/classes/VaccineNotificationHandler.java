package com.example.vaxnote.classes;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class VaccineNotificationHandler extends Application {
    public static final String CHANNEL_1_ID = "Vaccine Notifications";
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }
    public void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_1_ID,
                    "VaxNote Reminder",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Displaying Vaccine Reminders");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}

package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Calendar;

public class DeviceBootReceiver extends BroadcastReceiver {
    public static final String SHARED_PREFS="sharedPrefs";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            SharedPreferences sharedPreferences =context.getSharedPreferences(SHARED_PREFS,context.MODE_PRIVATE);
            int ora=sharedPreferences.getInt("ora",-1);
            int minuti=sharedPreferences.getInt("minuti",-1);
            if(ora>=0 && minuti >=0){
                Calendar calendar= Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,ora);
                calendar.set(Calendar.MINUTE,minuti);
                calendar.set(Calendar.SECOND,0);
                AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                Intent alarmIntent= new Intent(context,AlarmReceiver.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(context,1,alarmIntent,0);
                if(calendar.before(Calendar.getInstance())){
                    calendar.add(Calendar.DATE,1);
                }
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
            }
        }
    }
}

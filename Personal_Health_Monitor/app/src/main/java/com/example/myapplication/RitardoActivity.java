package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.RemoteInput;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class RitardoActivity extends AppCompatActivity {

    final String sharedPrefs="sharedPrefs";
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        sharedPreferences=getSharedPreferences(sharedPrefs,MODE_PRIVATE);
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(AlarmReceiver.notificaID);
        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE)+5);
        calendar.set(Calendar.SECOND,0);
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent= new Intent(this,AlarmReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1,alarmIntent,0);
        PendingIntent pendingIntent1=PendingIntent.getBroadcast(this,11,alarmIntent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent1);
        calendar.add(Calendar.DATE,1);
        int ora=sharedPreferences.getInt("ora",17);
        int min=sharedPreferences.getInt("minuti",00);
        calendar.set(Calendar.HOUR_OF_DAY,ora);
        calendar.set(Calendar.MINUTE,min);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        Toast.makeText(this,"Notifica posticipata con successo",Toast.LENGTH_SHORT).show();
        Log.v("Fatto","Tutto ok");
        finish();
    }
}
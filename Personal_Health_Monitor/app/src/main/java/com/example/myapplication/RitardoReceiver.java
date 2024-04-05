package com.example.myapplication;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import java.util.Calendar;

public class RitardoReceiver extends BroadcastReceiver {

    CharSequence ritardoText;
    int t;
    final String sharedPrefs="sharedPrefs";
    SharedPreferences sharedPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle remoteInput= RemoteInput.getResultsFromIntent(intent);
        sharedPreferences=context.getSharedPreferences(sharedPrefs,context.MODE_PRIVATE);

        if(remoteInput!=null){
            ritardoText= remoteInput.getCharSequence("risultato_ritardo");
        }
        try{
            t= Integer.parseInt((String)ritardoText);
            Calendar calendar= Calendar.getInstance();
            calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE)+t);
            calendar.set(Calendar.SECOND,0);
            AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent alarmIntent= new Intent(context,AlarmReceiver.class);
            PendingIntent pendingIntent=PendingIntent.getBroadcast(context,1,alarmIntent,0);
            PendingIntent pendingIntent1=PendingIntent.getBroadcast(context,11,alarmIntent,0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent1);
            calendar.add(Calendar.DATE,1);
            int ora=sharedPreferences.getInt("ora",17);
            int min=sharedPreferences.getInt("minuti",00);
            calendar.set(Calendar.HOUR_OF_DAY,ora);
            calendar.set(Calendar.MINUTE,min);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
            NotificationHelper NH = new NotificationHelper(context);
            NotificationCompat.Builder nc = NH.getCanale1Notification("Ricordati di me!", "Notifica posticipata di "+t+" minuti!");
            NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(AlarmReceiver.notificaID,nc.build());
            Toast.makeText(context,"Notifica posticipata con successo",Toast.LENGTH_SHORT).show();
                Log.v("Fatto","Tutto ok");
        }catch (NumberFormatException e){
            Toast.makeText(context,"Il tempo inserito ha un formato errato, impossibile posticipare la notifica",Toast.LENGTH_SHORT).show();
            Log.v("Non Fatto","Non Tutto ok");
        }
    }
}

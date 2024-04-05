package com.example.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper extends ContextWrapper {

    final String sharedPrefs = "sharedPrefs";
    public static final String canale1ID = "canale1ID";
    public static final String canale2ID = "canale2ID";

    private NotificationManagerCompat NM;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createChannel() {
        NotificationChannel canale1 = new NotificationChannel(canale1ID, canale1ID, NotificationManager.IMPORTANCE_DEFAULT);
        canale1.enableLights(true);
        canale1.enableVibration(true);
        canale1.setLightColor(R.color.colorPrimary);
        canale1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(canale1);
    }

    public NotificationManagerCompat getManager() {
        if (NM == null) {
            NM = NotificationManagerCompat.from(getApplicationContext());
        }
        return NM;
    }

    public NotificationCompat.Builder getCanale1Notification(String titolo, String messaggio) {
        Intent intent = new Intent(this, AggiungiActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            androidx.core.app.RemoteInput remoteInput = new androidx.core.app.RemoteInput.Builder("risultato_ritardo")
                    .setLabel("Di quanto posticipare la notifica(in minuti)?")
                    .build();

            Intent ritardoIntent = new Intent(this, RitardoReceiver.class);
            PendingIntent ritardoPendingIntent = PendingIntent.getBroadcast(this, 0, ritardoIntent, 0);

            NotificationCompat.Action ritardoAction = new NotificationCompat.Action.Builder(
                    R.drawable.ic_event_note,
                    "Posticipa",
                    ritardoPendingIntent
            ).addRemoteInput(remoteInput).build();

            return new NotificationCompat.Builder(this, canale1ID)
                    .setContentTitle(titolo)
                    .setContentText(messaggio)
                    .addAction(ritardoAction)
                    .setSmallIcon(R.drawable.ic_event_note)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);
        }
        else{
            Intent ritardoIntent = new Intent(this, RitardoActivity.class);
            PendingIntent ritardoPendingIntent = PendingIntent.getActivity(this, 0, ritardoIntent, 0);

            NotificationCompat.Action ritardoAction = new NotificationCompat.Action.Builder(
                    R.drawable.ic_event_note,
                    "Posticipa di 5 minuti",
                    ritardoPendingIntent
            ).build();


            return new NotificationCompat.Builder(this, canale1ID)
                    .setContentTitle(titolo)
                    .setContentText(messaggio)
                    .addAction(ritardoAction)
                    .setSmallIcon(R.drawable.ic_event_note)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);
        }
    }

    public NotificationCompat.Builder getCanale1NotificationMonitoriaggio(String titolo, String messaggio) {
        SharedPreferences sharedPreferences = getSharedPreferences(sharedPrefs, MODE_PRIVATE);
        long tel = sharedPreferences.getLong("numTel", 118);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + Long.toString(tel)));//change the number
        PendingIntent pendingIntent= PendingIntent.getActivity(this,7,callIntent,0);
        NotificationCompat.Action chiamaAction= new NotificationCompat.Action.Builder(
                R.drawable.ic_baseline,
                "Chiama Medico",
                pendingIntent
        ).build();

        return new NotificationCompat.Builder(this,canale1ID)
                .setContentTitle(titolo)
                .setContentText(messaggio)
                .addAction(chiamaAction)
                .setSmallIcon(R.drawable.ic_baseline)
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText(messaggio))
                .setAutoCancel(true);
    }
}

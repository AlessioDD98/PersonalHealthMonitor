package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

public class AlarmReceiver extends BroadcastReceiver {

    public static int notificaID=1;

        @Override
        public void onReceive(Context context, Intent intent) {
            NotificationHelper NH = new NotificationHelper(context);
            NotificationCompat.Builder nc = NH.getCanale1Notification("Ricordati di me!", "Ricordati di inserire almeno un Report al giorno!");
            NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(notificaID, nc.build());
        }
}

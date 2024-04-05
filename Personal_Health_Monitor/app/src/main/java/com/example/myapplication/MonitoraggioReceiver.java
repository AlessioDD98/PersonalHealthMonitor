package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MonitoraggioReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            String tipo=intent.getStringExtra("tipoMonitoraggio");
            if(tipo.equals("Pressione")){
                int notificaID=5;
                NotificationHelper NH = new NotificationHelper(context);
                NotificationCompat.Builder nc = NH.getCanale1NotificationMonitoriaggio("Soglia "+tipo+" superata!", "Hai superato la soglia impostata, ti consiglio di chiamare un medico!");
                NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
                notificationManagerCompat.notify(notificaID, nc.build());
            }
            else if(tipo.equals("Temperatura corporea")){
                int notificaID=6;
                NotificationHelper NH = new NotificationHelper(context);
                NotificationCompat.Builder nc = NH.getCanale1NotificationMonitoriaggio("Soglia "+tipo+" superata!", "Hai superato la soglia impostata, ti consiglio di chiamare un medico!");
                NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
                notificationManagerCompat.notify(notificaID, nc.build());
            }
           else if(tipo.equals("Frequenza cardiaca")){
                int notificaID=7;
                NotificationHelper NH = new NotificationHelper(context);
                NotificationCompat.Builder nc = NH.getCanale1NotificationMonitoriaggio("Soglia "+tipo+" superata!", "Hai superato la soglia impostata, ti consiglio di chiamare un medico!");
                NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
                notificationManagerCompat.notify(notificaID, nc.build());
            }
            else if(tipo.equals("Indice glicemico")){
                int notificaID=8;
                NotificationHelper NH = new NotificationHelper(context);
                NotificationCompat.Builder nc = NH.getCanale1NotificationMonitoriaggio("Soglia "+tipo+" superata!", "Hai superato la soglia impostata, ti consiglio di chiamare un medico!");
                NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
                notificationManagerCompat.notify(notificaID, nc.build());
            }
        }
}

package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AdapterReport extends RecyclerView.Adapter<AdapterReport.ReportViewHolder> {

    Context con;
    Database db;
    List<Report> report;
    public static final String SHARED_PREFS="sharedPrefs";

    private class getPressMediaAsyncTask extends AsyncTask<Date,Void, Float> {
        @Override
        protected Float doInBackground(Date... Dates) {
            return db.daoReport().MediaPressione(Dates[0],Dates[1]);
        }
    }

    private class getFCMediaAsyncTask extends AsyncTask<Date,Void, Float> {
        @Override
        protected Float doInBackground(Date... Dates) {
            return db.daoReport().MediaFrequenza(Dates[0],Dates[1]);
        }
    }

    private class getTCMediaAsyncTask extends AsyncTask<Date,Void, Float> {
        @Override
        protected Float doInBackground(Date... Dates) {
            return db.daoReport().MediaTC(Dates[0],Dates[1]);
        }
    }

    private class getIGMediaAsyncTask extends AsyncTask<Date,Void, Float> {
        @Override
        protected Float doInBackground(Date... Dates) {
            return db.daoReport().MediaIG(Dates[0],Dates[1]);
        }
    }

    private class ElimReportAsyncTask extends AsyncTask<Report, Void,Void> {
        @Override
        protected Void doInBackground(Report... reports) {
            MainActivity.db.daoReport().EliminaReport(reports[0]);
            return null;
        }
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder{
        public TextView contenuto;
        public Button modifica;
        public Button elimina;
        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            contenuto=itemView.findViewById(R.id.TR);
            modifica=itemView.findViewById(R.id.modifica);
            elimina=itemView.findViewById(R.id.elimina);
        }
    }

    public AdapterReport(Context con, Database db, List<Report> report){
        this.con=con;
        this.db=db;
        this.report=report;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.un_report,parent,false);
        ReportViewHolder rvh= new ReportViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, final int position) {
        final Report rd= this.report.get(position);
        String r="";
       // r+=Integer.toString(rd.getId());
        try{
            r+="\n"+rd.getPressione().getNome()+": "+rd.getPressione().getValore()+" mmHg";
        }catch (NullPointerException e){
        }
        try{
            r+="\n"+rd.getTemperaturaCorporea().getNome()+": "+rd.getTemperaturaCorporea().getValore()+" °C";
        }catch (NullPointerException e){
        }
        try{
            r+="\n"+rd.getIndiceGlicemico().getNome()+": "+rd.getIndiceGlicemico().getValore();
        }catch (NullPointerException e){
        }
        try{
            r+="\n"+rd.getPeso().getNome()+": "+rd.getPeso().getValore()+" Kg";
        }catch (NullPointerException e){
        }
        try{
            r+="\n"+rd.getFrequenzaCardiaca().getNome()+": "+rd.getFrequenzaCardiaca().getValore()+" BPM";
        }catch (NullPointerException e){
        }
        try{
            r+="\n"+rd.getOssigenazioneSangue().getNome()+": "+rd.getOssigenazioneSangue().getValore();
        }catch (NullPointerException e){
        }
        if(!rd.getNote().equals("")){
            r+="\nNote: "+rd.getNote();
        }
        holder.contenuto.setText(r);
        holder.elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ElimReportAsyncTask().execute(rd);
                report.remove(rd);
                notifyItemRemoved(position);
                Log.v("Eliminato","SI");
                if(getItemCount()==0){
                    ((Activity)con).finish();
                    Intent intent=new Intent(con,VisualizzaReport.class);
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String strDate = dateFormat.format(rd.getData());
                    intent.putExtra("data",strDate);
                    con.startActivity(intent);
                }
                if(getItemCount()==1){
                    ((Activity)con).finish();
                    Intent intent=new Intent(con,VisualizzaReport.class);
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String strDate = dateFormat.format(rd.getData());
                    intent.putExtra("data",strDate);
                    Gson gson=new Gson();
                    String tuttiToString=gson.toJson(report);
                    intent.putExtra("report",tuttiToString);
                    con.startActivity(intent);
                }
                //Monitoraggio
                SharedPreferences sharedPreferences =con.getSharedPreferences(SHARED_PREFS,con.MODE_PRIVATE);
                boolean sP=sharedPreferences.getBoolean("isP",false);
                boolean sTC=sharedPreferences.getBoolean("isTC",false);
                boolean sFC=sharedPreferences.getBoolean("isFC",false);
                boolean sIG=sharedPreferences.getBoolean("isIG",false);
                int periodo=sharedPreferences.getInt("Periodo",7);
                Calendar calendar=Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                Date d1=calendar.getTime();
                calendar.add(Calendar.DATE,-periodo+1);
                calendar.set(Calendar.HOUR_OF_DAY, 00);
                calendar.set(Calendar.MINUTE, 00);
                Date d0=calendar.getTime();
                if(sP){
                    monitoraggio(0,d0,d1);
                }
                if(sTC){
                    monitoraggio(1,d0,d1);
                }
                if(sFC){
                    monitoraggio(2,d0,d1);
                }
                if(sIG){
                    monitoraggio(3,d0,d1);
                }
            }
        });

        holder.modifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(con,ModificaActivity.class);
                Gson gson=new Gson();
                String reportToString=gson.toJson(rd);
                intent.putExtra("report_singolo",reportToString);
                String tuttiToString=gson.toJson(report);
                intent.putExtra("report",tuttiToString);
                intent.putExtra("pos",position);
                con.startActivity(intent);
                notifyItemChanged(position);
                ((Activity)con).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.report.size();
    }

    public void monitoraggio(int tipo, Date inizio, Date fine){
        SharedPreferences sharedPreferences=con.getSharedPreferences(SHARED_PREFS,con.MODE_PRIVATE);
        int soglia=0;
        float media=0;
        switch (tipo){
            case 0:
                soglia=sharedPreferences.getInt("valSogliaPress",130);
                try {
                    media=new AdapterReport.getPressMediaAsyncTask().execute(inizio,fine).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.v("Media p","media: "+media);
                if(media>soglia){
                    Intent intent= new Intent(con.getApplicationContext(),MonitoraggioReceiver.class);
                    intent.putExtra("tipoMonitoraggio","Pressione");
                    con.sendBroadcast(intent);
                }
                break;
            case 1:
                soglia=sharedPreferences.getInt("valSogliaTC",36);
                try {
                    media=new AdapterReport.getTCMediaAsyncTask().execute(inizio,fine).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.v("Media p","media: "+media);
                if(media>soglia){
                    Intent intent= new Intent(con.getApplicationContext(),MonitoraggioReceiver.class);
                    intent.putExtra("tipoMonitoraggio","Temperatura corporea");
                    con.sendBroadcast(intent);
                }
                break;
            case 2:
                soglia=sharedPreferences.getInt("valSogliaFC",100);
                try {
                    media=new AdapterReport.getFCMediaAsyncTask().execute(inizio,fine).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.v("Media p","media: "+media);
                if(media>soglia){
                    Intent intent= new Intent(con.getApplicationContext(),MonitoraggioReceiver.class);
                    intent.putExtra("tipoMonitoraggio","Frequenza cardiaca");
                    con.sendBroadcast(intent);
                }
                break;
            case 3:
                soglia=sharedPreferences.getInt("valSogliaIG",110);
                try {
                    media=new AdapterReport.getIGMediaAsyncTask().execute(inizio,fine).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.v("Media p","media: "+media);
                if(media>soglia){
                    Intent intent= new Intent(con.getApplicationContext(),MonitoraggioReceiver.class);
                    intent.putExtra("tipoMonitoraggio","Indice glicemico");
                    con.sendBroadcast(intent);
                }
                break;
        }
    }
}

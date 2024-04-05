package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.myapplication.MainActivity.db;

public class ModificaActivity extends AppCompatActivity {
    private androidx.appcompat.widget.Toolbar tb;

    public static final String SHARED_PREFS="sharedPrefs";
    Button mod;
    EditText pressione;
    EditText tempcorp;
    EditText indglic;
    EditText peso;
    EditText freqcard;
    EditText ossisang;
    EditText note;

    private class ModPressAsyncTask extends AsyncTask<String, Void,Void> {
        @Override
        protected Void doInBackground(String... strings) {
            MainActivity.db.daoReport().AggiornaPress(Double.parseDouble(strings[0]),Integer.parseInt(strings[1]));
            return null;
        }
    }

    private class ModTempAsyncTask extends AsyncTask<String, Void,Void> {
        @Override
        protected Void doInBackground(String... strings) {
            MainActivity.db.daoReport().AggiornaTemp(Double.parseDouble(strings[0]),Integer.parseInt(strings[1]));
            return null;
        }
    }

    private class ModOSAsyncTask extends AsyncTask<String, Void,Void> {
        @Override
        protected Void doInBackground(String... strings) {
            MainActivity.db.daoReport().AggiornaOS(Double.parseDouble(strings[0]),Integer.parseInt(strings[1]));
            return null;
        }
    }

    private class ModIGAsyncTask extends AsyncTask<String, Void,Void> {
        @Override
        protected Void doInBackground(String... strings) {
            MainActivity.db.daoReport().AggiornaIG(Double.parseDouble(strings[0]),Integer.parseInt(strings[1]));
            return null;
        }
    }

    private class ModPesoAsyncTask extends AsyncTask<String, Void,Void> {
        @Override
        protected Void doInBackground(String... strings) {
            MainActivity.db.daoReport().AggiornaPeso(Double.parseDouble(strings[0]),Integer.parseInt(strings[1]));
            return null;
        }
    }

    private class ModFreqAsyncTask extends AsyncTask<String, Void,Void> {
        @Override
        protected Void doInBackground(String... strings) {
            MainActivity.db.daoReport().AggiornaFreq(Double.parseDouble(strings[0]),Integer.parseInt(strings[1]));
            return null;
        }
    }

    private class ModNoteAsyncTask extends AsyncTask<String, Void,Void> {
        @Override
        protected Void doInBackground(String... strings) {
            MainActivity.db.daoReport().AggiornaNote(strings[0],Integer.parseInt(strings[1]));
            return null;
        }
    }

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

    public int modifica(int k,  List<Report> tutti, int position){
        k++;
        AdapterReport AR=new AdapterReport(getApplicationContext(),db,tutti);
        AR.notifyDataSetChanged();
        AR.notifyItemChanged(position);
        return k;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica);
        tb=findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        pressione=(EditText)findViewById(R.id.mPressione);
        tempcorp=findViewById(R.id.mTemperatura);
        indglic=findViewById(R.id.mIG);
        freqcard=findViewById(R.id.mFC);
        peso=findViewById(R.id.mPeso);
        ossisang=findViewById(R.id.mOS);
        note=findViewById(R.id.mNote);
        //Log.v("Report","Id:"+report.getId()+"\nData: "+report.getData()+"\nTemp: "+report.getTemperaturaCorporea().getValore());
        mod=findViewById(R.id.bottone_modifica_report);
        mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k=0;
                Gson gson= new Gson();
                Intent intent=getIntent();
                String reportToString=intent.getStringExtra("report_singolo");
                String dati=intent.getStringExtra("report");
                int position=intent.getIntExtra("pos",5);
                Log.v("pos","p: "+position);
                List<Report> tutti=gson.fromJson(dati,new TypeToken<List<Report>>(){}.getType());
                Report report=gson.fromJson(reportToString,Report.class);
                try{
                    double val=Double.parseDouble(pressione.getText().toString());
                    Log.v("Pressione","P:"+val);
                    new ModPressAsyncTask().execute(pressione.getText().toString(),Integer.toString(report.getId()));
                    k=modifica(k,tutti,position);
                }catch (NumberFormatException e){
                }
                try{
                    double val=Double.parseDouble(tempcorp.getText().toString());
                    new ModTempAsyncTask().execute(tempcorp.getText().toString(),Integer.toString(report.getId()));
                    k=modifica(k,tutti,position);
                }catch (NumberFormatException e){
                }
                try{
                    double val=Double.parseDouble(indglic.getText().toString());
                    new ModIGAsyncTask().execute(indglic.getText().toString(),Integer.toString(report.getId()));
                    k=modifica(k,tutti,position);
                }catch (NumberFormatException e){
                }
                try{
                    double val=Double.parseDouble(peso.getText().toString());
                    new ModPesoAsyncTask().execute(peso.getText().toString(),Integer.toString(report.getId()));
                    k=modifica(k,tutti,position);
                }catch (NumberFormatException e){
                }
                try{
                    double val=Double.parseDouble(ossisang.getText().toString());
                    new ModOSAsyncTask().execute(ossisang.getText().toString(),Integer.toString(report.getId()));
                    k=modifica(k,tutti,position);
                }catch (NumberFormatException e){
                }
                try{
                    double val=Double.parseDouble(freqcard.getText().toString());
                    new ModFreqAsyncTask().execute(freqcard.getText().toString(),Integer.toString(report.getId()));
                    k=modifica(k,tutti,position);
                }catch (NumberFormatException e){
                }
                try{
                    String val=note.getText().toString().trim();
                    if(!val.equals("")){
                        new ModNoteAsyncTask().execute(val,Integer.toString(report.getId()));
                        k=modifica(k,tutti,position);
                    }
                }catch (NumberFormatException e){
                }
                if(k>0){
                    Toast.makeText(getApplicationContext(),"Modifica effettuata con successo",Toast.LENGTH_SHORT).show();
                    //Monitoraggio
                    SharedPreferences sharedPreferences =getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
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
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Compila i campi da modificare",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                startActivity(new Intent(this,SettingsActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void monitoraggio(int tipo, Date inizio, Date fine){
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        int soglia=0;
        float media=0;
        switch (tipo){
            case 0:
                soglia=sharedPreferences.getInt("valSogliaPress",130);
                try {
                    media=new ModificaActivity.getPressMediaAsyncTask().execute(inizio,fine).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.v("Media p","media: "+media);
                if(media>soglia){
                    Intent intent= new Intent(getApplicationContext(),MonitoraggioReceiver.class);
                    intent.putExtra("tipoMonitoraggio","Pressione");
                    sendBroadcast(intent);
                }
                break;
            case 1:
                soglia=sharedPreferences.getInt("valSogliaTC",36);
                try {
                    media=new ModificaActivity.getTCMediaAsyncTask().execute(inizio,fine).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.v("Media p","media: "+media);
                if(media>soglia){
                    Intent intent= new Intent(getApplicationContext(),MonitoraggioReceiver.class);
                    intent.putExtra("tipoMonitoraggio","Temperatura corporea");
                    sendBroadcast(intent);
                }
                break;
            case 2:
                soglia=sharedPreferences.getInt("valSogliaFC",100);
                try {
                    media=new ModificaActivity.getFCMediaAsyncTask().execute(inizio,fine).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.v("Media p","media: "+media);
                if(media>soglia){
                    Intent intent= new Intent(getApplicationContext(),MonitoraggioReceiver.class);
                    intent.putExtra("tipoMonitoraggio","Frequenza cardiaca");
                    sendBroadcast(intent);
                }
                break;
            case 3:
                soglia=sharedPreferences.getInt("valSogliaIG",110);
                try {
                    media=new ModificaActivity.getIGMediaAsyncTask().execute(inizio,fine).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.v("Media p","media: "+media);
                if(media>soglia){
                    Intent intent= new Intent(getApplicationContext(),MonitoraggioReceiver.class);
                    intent.putExtra("tipoMonitoraggio","Indice glicemico");
                    sendBroadcast(intent);
                }
                break;
        }
    }
}

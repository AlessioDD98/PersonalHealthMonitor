package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import static com.example.myapplication.MainActivity.db;

public class AggiungiActivity extends AppCompatActivity {

    public static final String SHARED_PREFS="sharedPrefs";
    private androidx.appcompat.widget.Toolbar tb;

    Button InserisciReport;
    EditText Pressione;
    EditText Temperatura_corporea;
    EditText FrequenzaCard;
    EditText Peso;
    EditText OssSang;
    EditText IG;
    EditText Note;


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

    private class InsReportAsyncTask extends AsyncTask<Report, Void,Void>{
        @Override
        protected Void doInBackground(Report... reports) {
            MainActivity.db.daoReport().InserisciReport(reports[0]);
            return null;
        }
    }

    private class getAllReportAsyncTask extends AsyncTask<Void,Void,List<Report>>{

        @Override
        protected List<Report> doInBackground(Void... voids) {
            return MainActivity.db.daoReport().tuttiReport();
        }
    }

    boolean isP=false;
    boolean isT=false;
    boolean isI=false;
    boolean isF=false;
    boolean isPP=false;
    boolean isO=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi);
        tb=findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        InserisciReport=findViewById(R.id.bottone_inserisci_report);
        InserisciReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0;
                double press=-1;
                double temperatura=-1;
                double ind_glic=-1;
                double OS=-1;
                double p=-1;
                double freq=-1;
                Pressione=findViewById(R.id.Pressione);
                try{
                    press = Double.parseDouble(Pressione.getText().toString());
                    i++;
                    isP=true;
                }catch (NumberFormatException e){
                }
                Temperatura_corporea=findViewById(R.id.Temperatura);
                try{
                    temperatura = Double.parseDouble(Temperatura_corporea.getText().toString());
                    i++;
                    isT=true;
                }catch (NumberFormatException e){
                }
                IG=findViewById(R.id.IG);
                try{
                    ind_glic = Double.parseDouble(IG.getText().toString());
                    i++;
                    isI=true;
                }catch (NumberFormatException e){
                }
                FrequenzaCard=findViewById(R.id.FC);
                try{
                    freq = Double.parseDouble(FrequenzaCard.getText().toString());
                    i++;
                    isF=true;
                }catch (NumberFormatException e){
                }
                Peso=findViewById(R.id.Peso);
                try{
                    p = Double.parseDouble(Peso.getText().toString());
                    i++;
                    isPP=true;
                }catch (NumberFormatException e){
                }
                OssSang=findViewById(R.id.OS);
                try{
                    OS = Double.parseDouble(OssSang.getText().toString());
                    i++;
                    isO=true;
                }catch (NumberFormatException e){
                }
                Note=findViewById(R.id.Note);
                String note = Note.getText().toString().trim();
                SharedPreferences sharedPreferences =getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                boolean addToday=sharedPreferences.getBoolean("addToday",false);
                if(i>1){
                    Report r=new Report();
                    Date d=new Date();
                    r.setData(d);
                    r.setNote(note);
                    if(isP){
                        r.setPressione(new Pressione(press));
                        isP=false;
                    }
                    if(isT){
                        r.setTemperaturaCorporea(new TemperaturaCorporea(temperatura));
                        isT=false;
                    }
                    if(isI){
                        r.setIndiceGlicemico(new IndiceGlicemico(ind_glic));
                        isI=false;
                    }
                    if (isF) {
                        r.setFrequenzaCardiaca(new FrequenzaCardiaca(freq));
                        isF=false;
                    }
                    if(isPP){
                        r.setPeso(new Peso(p));
                        isPP=false;
                    }
                    if(isO){
                        r.setOssigenazioneSangue(new OssigenazioneSangue(OS));
                        isO=false;
                    }
                    new InsReportAsyncTask().execute(r);
                    List<Report> tutti=null;
                    try {
                        tutti = new getAllReportAsyncTask().execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //Se sto inserendo il primo report del giorno
                    if(!addToday){
                        //Cancello l'alarm
                        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        Intent intent= new Intent(getApplicationContext(),AlarmReceiver.class);
                        PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),1,intent,0);
                        PendingIntent pendingIntent1=PendingIntent.getBroadcast(getApplicationContext(),11,intent,0);
                        alarmManager.cancel(pendingIntent);
                        alarmManager.cancel(pendingIntent1);
                        //Inserisco l'alarm a partire dal giorno seguente
                        int ora=sharedPreferences.getInt("ora",-1);
                        int minuti=sharedPreferences.getInt("minuti",-1);
                        if(ora>=0 && minuti >=0){
                            Calendar calendar= Calendar.getInstance();
                            calendar.set(Calendar.HOUR_OF_DAY,ora);
                            calendar.set(Calendar.MINUTE,minuti);
                            calendar.set(Calendar.SECOND,0);
                            calendar.add(Calendar.DATE,1);
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
                        }
                        sharedPreferences.edit().putBoolean("addToday",true).apply();
                    }
                    Toast.makeText(getApplicationContext(), "Report inserito! ", Toast.LENGTH_SHORT).show();
                    Pressione.setText("");
                    Temperatura_corporea.setText("");
                    IG.setText("");
                    Peso.setText("");
                    FrequenzaCard.setText("");
                    OssSang.setText("");
                    Note.setText("");
                    //Monitoraggio
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
                else{
                    Toast.makeText(getApplicationContext(), "Compilare almeno 2 campi ", Toast.LENGTH_SHORT).show();
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
                    media=new getPressMediaAsyncTask().execute(inizio,fine).get();
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
                    media=new getTCMediaAsyncTask().execute(inizio,fine).get();
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
                    media=new getFCMediaAsyncTask().execute(inizio,fine).get();
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
                    media=new getIGMediaAsyncTask().execute(inizio,fine).get();
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

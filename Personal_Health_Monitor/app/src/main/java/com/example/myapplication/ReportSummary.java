package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.myapplication.MainActivity.db;

public class ReportSummary extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar tb;

    TextView titolo;
    TextView contenuto;
    List<Report> tutti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_summary);
        tb=findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        String r="";
        double mediaPress=0;
        double mediaTemp=0;
        double mediaPes=0;
        double mediaFreq=0;
        double mediaOs=0;
        double mediaIG=0;
        int k=0;
        int kt=0;
        int kp=0;
        int kos=0;
        int kfreq=0;
        int kg=0;
        titolo=findViewById(R.id.TitoloSummary);
        contenuto=findViewById(R.id.ContenutoSummary);
        Intent getData=getIntent();
        String d=getData.getStringExtra("data");
        titolo.setText("Report Summary di data: "+d);
        Gson gson=new Gson();
        String dati=getData.getStringExtra("report");
        tutti=gson.fromJson(dati,new TypeToken<List<Report>>(){}.getType());
        for(int i=0;i<tutti.size();i++){
            try{
                mediaPress+=tutti.get(i).getPressione().getValore();
                k++;
                Log.v("Pressione List","valore"+tutti.get(i).getPressione().getValore());
            }catch (NullPointerException e){
            }
            try{
                mediaTemp+=tutti.get(i).getTemperaturaCorporea().getValore();
                kt++;
             //   Log.v("Pressione List","valore"+tutti.get(i).getPressione().getValore());
            }catch (NullPointerException e){
            }
            try{
                mediaIG+=tutti.get(i).getIndiceGlicemico().getValore();
                kg++;
             //   Log.v("Pressione List","valore"+tutti.get(i).getPressione().getValore());
            }catch (NullPointerException e){
            }
            try{
                mediaFreq+=tutti.get(i).getFrequenzaCardiaca().getValore();
                kfreq++;
                //Log.v("Pressione List","valore"+tutti.get(i).getPressione().getValore());
            }catch (NullPointerException e){
            }
            try{
                mediaOs+=tutti.get(i).getOssigenazioneSangue().getValore();
                kos++;
                //Log.v("Pressione List","valore"+tutti.get(i).getPressione().getValore());
            }catch (NullPointerException e){
            }
            try{
                mediaPes+=tutti.get(i).getPeso().getValore();
                kp++;
               // Log.v("Pressione List","valore"+tutti.get(i).getPressione().getValore());
            }catch (NullPointerException e){
            }
        }
        if(k>0){
            mediaPress=mediaPress/k;
            r+="Pressione media: "+String.format("%.2f",mediaPress)+" mmHg";
        }
        if(kt>0){
            mediaTemp=mediaTemp/kt;
            r+="\n\nTemperatura Corporea media: "+String.format("%.2f",mediaTemp)+" °C";
        }
        if(kg>0){
            mediaIG=mediaIG/kg;
            r+="\n\nIndice Glicemico medio: "+String.format("%.2f",mediaIG);
        }
        if(kp>0){
            mediaPes/=kp;
            r+="\n\nPeso medio: "+String.format("%.2f",mediaPes)+" Kg";
        }
        if(kfreq>0){
            mediaFreq=mediaFreq/kfreq;
            r+="\n\nFrequenza cardiaca media: "+String.format("%.2f",mediaFreq)+" BPM";
        }
        if(kos>0){
            mediaOs/=kos;
            r+="\n\nOssigenazione Sangue media: "+String.format("%.2f",mediaOs);
        }
        contenuto.setText(r);

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
}

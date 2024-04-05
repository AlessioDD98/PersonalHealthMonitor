package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.example.myapplication.MainActivity.db;

public class GraficiActivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar tb;
    BarChart barChart0;
    BarChart barChart1;
    BarChart barChart2;
    BarChart barChart3;
    BarChart barChart4;
    BarChart barChart5;
    PieChart torta;
    float val;
    int v;

    private class getNumReportAsyncTask extends AsyncTask<Date,Void, Integer> {
        @Override
        protected Integer doInBackground(Date... Dates) {
            return db.daoReport().NumReportInseriti(Dates[0],Dates[1]);
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

    private class getPesoMediaAsyncTask extends AsyncTask<Date,Void, Float> {
        @Override
        protected Float doInBackground(Date... Dates) {
            return db.daoReport().MediaPeso(Dates[0],Dates[1]);
        }
    }

    private class getOSMediaAsyncTask extends AsyncTask<Date,Void, Float> {
        @Override
        protected Float doInBackground(Date... Dates) {
            return db.daoReport().MediaOS(Dates[0],Dates[1]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafici);
        tb=findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        barChart0=findViewById(R.id.graficoBarrePress);
        barChart1=findViewById(R.id.graficoBarreTC);
        barChart2=findViewById(R.id.graficoBarreIG);
        barChart3=findViewById(R.id.graficoBarrePeso);
        barChart4=findViewById(R.id.graficoBarreFC);
        barChart5=findViewById(R.id.graficoBarreOS);
        torta=findViewById(R.id.torta);
        graficoBarre(barChart0,"Variazione Pressione",0);
        graficoBarre(barChart1,"Variazione Temperatura corporea",1);
        graficoBarre(barChart2,"Variazione Indice Glicemico",2);
        graficoBarre(barChart3,"Variazione Peso",3);
        graficoBarre(barChart4,"Variazione Frequenza cardiaca",4);
        graficoBarre(barChart5,"Variazione Ossigenazione sangue",5);
        //Grafico a torta
        ArrayList<PieEntry> pos=new ArrayList<>();
        for(int i=6;i>=0;i--){
            Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.DATE,-i);
            calendar.set(Calendar.HOUR_OF_DAY, 00);
            calendar.set(Calendar.MINUTE, 00);
            Date d= calendar.getTime();
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            Date d1=calendar.getTime();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String strDate = dateFormat.format(d);
            try {
                v=new getNumReportAsyncTask().execute(d,d1).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(v>0){
                pos.add(new PieEntry(v,strDate));
            }
        }
        PieDataSet pieDataSet= new PieDataSet(pos,"Numero Report inseriti al giorno");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);
        PieData pieData= new PieData(pieDataSet);
        torta.setData(pieData);
        torta.getDescription().setEnabled(false);
        torta.setCenterText("Numero Report inseriti al giorno");
        torta.getLegend().setEnabled(false);
        torta.animate();
    }

    public void graficoBarre(BarChart b,String label, int tipo){
        ArrayList<BarEntry> pos= new ArrayList<>();
        ArrayList<String> labelsName= new ArrayList<>();
        int k=0;
        for(int i=6;i>=0;i--){
            Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.DATE,-i);
            calendar.set(Calendar.HOUR_OF_DAY, 00);
            calendar.set(Calendar.MINUTE, 00);
            Date d= calendar.getTime();
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            Date d1=calendar.getTime();
            switch (tipo){
                case 0:
                    try {
                        val=new getPressMediaAsyncTask().execute(d,d1).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    try {
                        val=new getTCMediaAsyncTask().execute(d,d1).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        val=new getIGMediaAsyncTask().execute(d,d1).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        val=new getPesoMediaAsyncTask().execute(d,d1).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        val=new getFCMediaAsyncTask().execute(d,d1).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    try {
                        val=new getOSMediaAsyncTask().execute(d,d1).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String strDate = dateFormat.format(d);
            labelsName.add(strDate);
            pos.add(new BarEntry(k,val));
            k++;
        }
        BarDataSet barDataSet= new BarDataSet(pos,label);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        Description description= new Description();
        description.setText("Data");
        b.setDescription(description);
        BarData barData= new BarData(barDataSet);
        b.setData(barData);
        XAxis xAxis=b.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsName));
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setLabelCount(labelsName.size());
        xAxis.setLabelRotationAngle(270);
        b.animateY(2000);
        b.invalidate();
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
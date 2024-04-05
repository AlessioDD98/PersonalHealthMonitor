package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.myapplication.MainActivity.db;

public class VisualizzaFiltrata extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar tb;

    TextView titolo;
    List<Report> tutti;
    RecyclerView rv;
    AdapterReport rvAdapter;
    RecyclerView.LayoutManager rvLayoutManager;

    private class getAllReportAsyncTask extends AsyncTask<String,Void, List<Report>> {
        @Override
        protected List<Report> doInBackground(String... strings) {
            String[] ele=strings[0].split("/");
            Calendar calendar=Calendar.getInstance();
            calendar.set(Integer.parseInt(ele[2]),Integer.parseInt(ele[1])-1,Integer.parseInt(ele[0]),00,00);
            Date d0= calendar.getTime();
            calendar.set(Integer.parseInt(ele[2]),Integer.parseInt(ele[1])-1,Integer.parseInt(ele[0]),23,59);
            Date d1=calendar.getTime();
            return db.daoReport().filtraImp(d0,d1,Integer.parseInt(strings[2]));
        }
    }

    private class getAllPressioneAsyncTask extends AsyncTask<String,Void, List<Report>> {
        @Override
        protected List<Report> doInBackground(String... strings) {
            String[] ele=strings[0].split("/");
            Calendar calendar=Calendar.getInstance();
            calendar.set(Integer.parseInt(ele[2]),Integer.parseInt(ele[1])-1,Integer.parseInt(ele[0]),00,00);
            Date d0= calendar.getTime();
            calendar.set(Integer.parseInt(ele[2]),Integer.parseInt(ele[1])-1,Integer.parseInt(ele[0]),23,59);
            Date d1=calendar.getTime();
            return db.daoReport().filtraPressione(d0,d1);
        }
    }

    private class getAllTempAsyncTask extends AsyncTask<String,Void, List<Report>> {
        @Override
        protected List<Report> doInBackground(String... strings) {
            String[] ele=strings[0].split("/");
            Calendar calendar=Calendar.getInstance();
            calendar.set(Integer.parseInt(ele[2]),Integer.parseInt(ele[1])-1,Integer.parseInt(ele[0]),00,00);
            Date d0= calendar.getTime();
            calendar.set(Integer.parseInt(ele[2]),Integer.parseInt(ele[1])-1,Integer.parseInt(ele[0]),23,59);
            Date d1=calendar.getTime();
            return db.daoReport().filtraTemp(d0,d1);
        }
    }

    private class getAllIGAsyncTask extends AsyncTask<String,Void, List<Report>> {
        @Override
        protected List<Report> doInBackground(String... strings) {
            String[] ele=strings[0].split("/");
            Calendar calendar=Calendar.getInstance();
            calendar.set(Integer.parseInt(ele[2]),Integer.parseInt(ele[1])-1,Integer.parseInt(ele[0]),00,00);
            Date d0= calendar.getTime();
            calendar.set(Integer.parseInt(ele[2]),Integer.parseInt(ele[1])-1,Integer.parseInt(ele[0]),23,59);
            Date d1=calendar.getTime();
            return db.daoReport().filtraIG(d0,d1);
        }
    }

    private class getAllFCAsyncTask extends AsyncTask<String,Void, List<Report>> {
        @Override
        protected List<Report> doInBackground(String... strings) {
            String[] ele=strings[0].split("/");
            Calendar calendar=Calendar.getInstance();
            calendar.set(Integer.parseInt(ele[2]),Integer.parseInt(ele[1])-1,Integer.parseInt(ele[0]),00,00);
            Date d0= calendar.getTime();
            calendar.set(Integer.parseInt(ele[2]),Integer.parseInt(ele[1])-1,Integer.parseInt(ele[0]),23,59);
            Date d1=calendar.getTime();
            return db.daoReport().filtraFC(d0,d1);
        }
    }

    private class getAllPesoAsyncTask extends AsyncTask<String,Void, List<Report>> {
        @Override
        protected List<Report> doInBackground(String... strings) {
            String[] ele=strings[0].split("/");
            Calendar calendar=Calendar.getInstance();
            calendar.set(Integer.parseInt(ele[2]),Integer.parseInt(ele[1])-1,Integer.parseInt(ele[0]),00,00);
            Date d0= calendar.getTime();
            calendar.set(Integer.parseInt(ele[2]),Integer.parseInt(ele[1])-1,Integer.parseInt(ele[0]),23,59);
            Date d1=calendar.getTime();
            return db.daoReport().filtraPeso(d0,d1);
        }
    }

    private class getAllOSAsyncTask extends AsyncTask<String,Void, List<Report>> {
        @Override
        protected List<Report> doInBackground(String... strings) {
            String[] ele=strings[0].split("/");
            Calendar calendar=Calendar.getInstance();
            calendar.set(Integer.parseInt(ele[2]),Integer.parseInt(ele[1])-1,Integer.parseInt(ele[0]),00,00);
            Date d0= calendar.getTime();
            calendar.set(Integer.parseInt(ele[2]),Integer.parseInt(ele[1])-1,Integer.parseInt(ele[0]),23,59);
            Date d1=calendar.getTime();
            return db.daoReport().filtraOS(d0,d1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_filtrata);
        tb=findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        titolo=findViewById(R.id.VFTitolo);
        Intent intent=getIntent();
        String d= intent.getStringExtra("data");
        int imp=intent.getIntExtra("imp",0);
        String t=intent.getStringExtra("tipo");
        String tf=intent.getStringExtra("tf");
        titolo.setText(t);
        if(tf.equals("FI")){
            try {
                tutti=new getAllReportAsyncTask().execute(d,d,Integer.toString(imp)).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            titolo.setText(t+" o +");
        }
        if(tf.equals("FP")){
            try {
                tutti=new getAllPressioneAsyncTask().execute(d).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(tf.equals("FT")){
            try {
                tutti=new getAllTempAsyncTask().execute(d).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(tf.equals("FIG")){
            try {
                tutti=new getAllIGAsyncTask().execute(d).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(tf.equals("FFC")){
            try {
                tutti=new getAllFCAsyncTask().execute(d).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(tf.equals("FPE")){
            try {
                tutti=new getAllPesoAsyncTask().execute(d).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(tf.equals("FOS")){
            try {
                tutti=new getAllOSAsyncTask().execute(d).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        rv=findViewById(R.id.fListaReport);
        rv.setHasFixedSize(true);
        rvLayoutManager= new LinearLayoutManager(this);
        if(tutti.size()==0){
            titolo.setText(t+"\nNon ci sono report!");
        }
        else{
            rvAdapter=new AdapterReport(this,db,tutti);
            rv.setLayoutManager(rvLayoutManager);
            rv.setAdapter(rvAdapter);

        }
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

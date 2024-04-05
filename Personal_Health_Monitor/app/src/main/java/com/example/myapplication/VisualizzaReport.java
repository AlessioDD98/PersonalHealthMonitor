package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.myapplication.MainActivity.db;

public class VisualizzaReport extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar tb;
    List<Report> tutti;
    RecyclerView rv;
    AdapterReport rvAdapter;
    RecyclerView.LayoutManager rvLayoutManager;
    TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_report);
        tb=findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        data=findViewById(R.id.DataR);
        Intent getData=getIntent();
        final String d=getData.getStringExtra("data");
        Gson gson=new Gson();
        String dati=getData.getStringExtra("report");
        tutti=gson.fromJson(dati,new TypeToken<List<Report>>(){}.getType());
        data.setText("Report di data: "+d);
        rv=findViewById(R.id.listaReport);
        rv.setHasFixedSize(true);
        rvLayoutManager= new LinearLayoutManager(this);
        if(tutti==null){
            data.setText("Report di data: "+d+"\nNon ci sono report!");
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

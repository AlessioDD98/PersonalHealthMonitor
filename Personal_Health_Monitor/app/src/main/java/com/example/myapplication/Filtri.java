package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Filtri extends AppCompatActivity {
    private androidx.appcompat.widget.Toolbar tb;
    Button filtra5;
    Button filtra4;
    Button filtra3;
    Button filtra2;
    Button filtra1;
    Button FP;
    Button FT;
    Button FIG;
    Button FFC;
    Button FOS;
    Button FPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtri);
        tb=findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        Intent intent=getIntent();
        final String d=intent.getStringExtra("data");
        filtra5=findViewById(R.id.filtroImp5);
        filtra4=findViewById(R.id.filtroImp4);
        filtra3=findViewById(R.id.filtroImp3);
        filtra2=findViewById(R.id.filtroImp2);
        filtra1=findViewById(R.id.filtroImp1);
        FP=findViewById(R.id.vPressione);
        FT=findViewById(R.id.vTemp);
        FIG=findViewById(R.id.vIG);
        FFC=findViewById(R.id.vFC);
        FOS=findViewById(R.id.vOS);
        FPE=findViewById(R.id.vPeso);
        FP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),VisualizzaFiltrata.class);
                intent.putExtra("data",d);
                intent.putExtra("tipo","Report che contengono Pressione");
                intent.putExtra("tf","FP");
                startActivity(intent);
            }
        });
        FT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),VisualizzaFiltrata.class);
                intent.putExtra("data",d);
                intent.putExtra("tipo","Report che contengono Temperatura Corporea");
                intent.putExtra("tf","FT");
                startActivity(intent);
            }
        });
        FIG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),VisualizzaFiltrata.class);
                intent.putExtra("data",d);
                intent.putExtra("tipo","Report che contengono Indice glicemico");
                intent.putExtra("tf","FIG");
                startActivity(intent);
            }
        });
        FFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),VisualizzaFiltrata.class);
                intent.putExtra("data",d);
                intent.putExtra("tipo","Report che contengono Frequenza cardiaca");
                intent.putExtra("tf","FFC");
                startActivity(intent);
            }
        });
        FPE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),VisualizzaFiltrata.class);
                intent.putExtra("data",d);
                intent.putExtra("tipo","Report che contengono Peso");
                intent.putExtra("tf","FPE");
                startActivity(intent);
            }
        });
        FOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),VisualizzaFiltrata.class);
                intent.putExtra("data",d);
                intent.putExtra("tipo","Report che contengono Ossigenazione sangue");
                intent.putExtra("tf","FOS");
                startActivity(intent);
            }
        });
        filtra5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),VisualizzaFiltrata.class);
                intent.putExtra("data",d);
                intent.putExtra("imp",5);
                intent.putExtra("tipo","Report con importanza 5");
                intent.putExtra("tf","FI");
                startActivity(intent);
            }
        });
        filtra4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),VisualizzaFiltrata.class);
                intent.putExtra("data",d);
                intent.putExtra("imp",4);
                intent.putExtra("tipo","Report con importanza 4");
                intent.putExtra("tf","FI");
                startActivity(intent);
            }
        });
        filtra3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),VisualizzaFiltrata.class);
                intent.putExtra("data",d);
                intent.putExtra("imp",3);
                intent.putExtra("tipo","Report con importanza 3");
                intent.putExtra("tf","FI");
                startActivity(intent);
            }
        });
        filtra2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),VisualizzaFiltrata.class);
                intent.putExtra("data",d);
                intent.putExtra("imp",2);
                intent.putExtra("tipo","Report con importanza 2");
                intent.putExtra("tf","FI");
                startActivity(intent);
            }
        });
        filtra1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),VisualizzaFiltrata.class);
                intent.putExtra("data",d);
                intent.putExtra("imp",1);
                intent.putExtra("tipo","Report con importanza 1");
                intent.putExtra("tf","FI");
                startActivity(intent);
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
}

package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.Manifest;
import android.app.AlarmManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static Database db;
    private androidx.appcompat.widget.Toolbar tb;
    public static final String SHARED_PREFS="sharedPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db= Room.databaseBuilder(getApplicationContext(),Database.class,"appdb").build();
        tb=findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        isPermissionGranted();
        SharedPreferences sharedPreferences =getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        boolean isFirstTime=sharedPreferences.getBoolean("isFirstTime",true);
        if(isFirstTime){
            startActivity(new Intent(this,SettingsActivity.class));
        }
        Date oggi=new Date();
        Date memorizzataOggi= new Date(sharedPreferences.getLong("data",0));
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(oggi);
        cal2.setTime(memorizzataOggi);
        boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        if(!sameDay){
            sharedPreferences.edit().putBoolean("addToday",false).apply();
            sharedPreferences.edit().putLong("data",new Date().getTime()).apply();
        }
        Button vr=(Button)findViewById(R.id.VR);
        vr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                SelezionaReport newFragment = new SelezionaReport();
                transaction.replace(R.id.main_l,newFragment);
                transaction.addToBackStack("Visualizza_Report");
                transaction.commit();
            }
        });
        Button grafici=findViewById(R.id.Grafici);
        grafici.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),GraficiActivity.class);
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

    public void isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                return;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permesso ottenuto", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permesso rifiutato", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }
}

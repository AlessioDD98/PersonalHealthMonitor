package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private TextView tw;
    final String sharedPrefs="sharedPrefs";
    SharedPreferences sharedPreferences;
    private int ora;
    private int min;
    int telefono;
    EditText testo;
    EditText testoPress;
    EditText testoTC;
    EditText testoFC;
    EditText testoIG;
    TextView textPeriodo;
    Button modificaPeriodo;
    Switch switchPress;
    Button MSPress;
    TextView sogliaPress;
    Switch switchTC;
    Button MSTC;
    TextView sogliaTC;
    Switch switchFC;
    Button MSFC;
    TextView sogliaFC;
    Switch switchIG;
    Button MSIG;
    TextView sogliaIG;
    TextView numMedico;
    Button modNumero;
    EditText editNumTel;
    int valSogliaPress;
    int valSogliaTC;
    int valSogliaFC;
    int valSogliaIG;
    boolean isP;
    boolean isTC;
    boolean isFC;
    boolean isIG;
    AlertDialog.Builder builder;

    public Calendar creaNotificaConAlarm(int ora,int min){
        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,ora);
        calendar.set(Calendar.MINUTE,min);
        calendar.set(Calendar.SECOND,0);
        return calendar;
    }

    public void cancellaNotifica(){
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent= new Intent(this,AlarmReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1,intent,0);
        alarmManager.cancel(pendingIntent);
    }

    public void modificaGrafica(Switch s, Button b, TextView t,int val){
        if(!s.isChecked()){
            b.setVisibility(View.INVISIBLE);
            t.setVisibility(View.INVISIBLE);
        }
        else{
            b.setVisibility(View.VISIBLE);
            t.setText("Soglia: "+val);
            t.setVisibility(View.VISIBLE);
        }
    }

    public AlertDialog creaAlert(String titolo, final String messaggio, final String campo, final String risultato, EditText et, final int defVal, final TextView r){
        builder=new AlertDialog.Builder(this);
        builder.setTitle(titolo);
        et =new EditText(this);
        et.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(et);
        final EditText finalEt = et;
        builder.setPositiveButton("Salva", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int p=Integer.parseInt(finalEt.getText().toString());
                sharedPreferences.edit().putInt(campo,p).apply();
                r.setText(messaggio+sharedPreferences.getInt(campo,defVal));
                Toast.makeText(getApplicationContext(),risultato,Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Settings");
        sharedPreferences=getSharedPreferences(sharedPrefs,MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("isFirstTime",false).apply();
        numMedico=findViewById(R.id.telMedico);
        modNumero=findViewById(R.id.bottoneNumero);
        tw=findViewById(R.id.testoOrario);
        MSPress=findViewById(R.id.MSPress);
        sogliaPress=findViewById(R.id.sogliaPress);
        MSTC=findViewById(R.id.MSTC);
        sogliaTC=findViewById(R.id.sogliaTC);
        MSFC=findViewById(R.id.MSFC);
        sogliaFC=findViewById(R.id.sogliaFC);
        MSIG=findViewById(R.id.MSIG);
        sogliaIG=findViewById(R.id.sogliaIG);
        ora=sharedPreferences.getInt("ora",17);
        min=sharedPreferences.getInt("minuti",00);
        boolean addToday=sharedPreferences.getBoolean("addToday",false);
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent= new Intent(this,AlarmReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1,intent,0);
        Calendar calendar=creaNotificaConAlarm(ora,min);
        if(!addToday){
            if(calendar.before(Calendar.getInstance())){
                calendar.add(Calendar.DATE,1);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
            }
            else{
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
            }
        }
        else{
            calendar.add(Calendar.DATE,1);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        }
        long numTel=sharedPreferences.getLong("numTel",118);
        int periodo = sharedPreferences.getInt("Periodo", 7);
        valSogliaPress=sharedPreferences.getInt("valSogliaPress",130);
        valSogliaTC=sharedPreferences.getInt("valSogliaTC",36);
        valSogliaFC=sharedPreferences.getInt("valSogliaFC",100);
        valSogliaIG=sharedPreferences.getInt("valSogliaIG",110);
        isP=sharedPreferences.getBoolean("isP",false);
        isTC=sharedPreferences.getBoolean("isTC",false);
        isFC=sharedPreferences.getBoolean("isFC",false);
        isIG=sharedPreferences.getBoolean("isIG",false);
        switchPress=findViewById(R.id.switchPress);
        switchTC=findViewById(R.id.switchTC);
        switchFC=findViewById(R.id.switchFC);
        switchIG=findViewById(R.id.switchIG);
        switchPress.setChecked(isP);
        switchTC.setChecked(isTC);
        switchFC.setChecked(isFC);
        switchIG.setChecked(isIG);
        modificaGrafica(switchPress,MSPress,sogliaPress,valSogliaPress);
        modificaGrafica(switchTC,MSTC,sogliaTC,valSogliaTC);
        modificaGrafica(switchFC,MSFC,sogliaFC,valSogliaFC);
        modificaGrafica(switchIG,MSIG,sogliaIG,valSogliaIG);
        tw.setText("Orario in cui ricevere la notifica di mancato inserimento del Report  giornaliero: "+ DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime()));
        textPeriodo=findViewById(R.id.periodo);
        textPeriodo.setText("Periodo per il monitoraggio dei parametri (in giorni): "+ periodo);
        numMedico.setText("Numero telefonico medico da chiamare in caso di emergenza: "+numTel);
        //Alert per numero telefono
        builder=new AlertDialog.Builder(this);
        builder.setTitle("Inserisci il numero di telefono");
        editNumTel =new EditText(this);
        editNumTel.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(editNumTel);
        builder.setPositiveButton("Salva", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long p=Long.parseLong(editNumTel.getText().toString());
                sharedPreferences.edit().putLong("numTel",p).apply();
                numMedico.setText("Numero telefonico medico da chiamare in caso di emergenza: "+sharedPreferences.getLong("numTel",118));
                Toast.makeText(getApplicationContext(),"Numero telefonico modificato",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog an=builder.create();
        modNumero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                an.show();
            }
        });
        //Alert per il Periodo
        final AlertDialog ad=creaAlert("Inserisci il periodo","Periodo per il monitoraggio dei parametri (in giorni): ","Periodo","Periodo modificato",testo,7,textPeriodo);
        modificaPeriodo=findViewById(R.id.bottonePeriodo);
        modificaPeriodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.show();
            }
        });
        //Seleziona Orario per la notifica
        Button selezionaOrario = findViewById(R.id.SO);
        selezionaOrario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker= new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"TimePicker");
            }
        });
        //Alert per la soglia Pressione
        final AlertDialog ap=creaAlert("Inserisci soglia Pressione","Soglia: ","valSogliaPress","Soglia modificata",testoPress,130,sogliaPress);
        switchPress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean("isP",isChecked).apply();
                modificaGrafica(switchPress,MSPress,sogliaPress,valSogliaPress);
            }
        });
        MSPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ap.show();
            }
        });
        //Alert per la soglia Temperatura Corporea
        final AlertDialog atc=creaAlert("Inserisci soglia Temperatura Corporea","Soglia: ","valSogliaTC","Soglia modificata",testoTC,36,sogliaTC);
        switchTC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean("isTC",isChecked).apply();
                modificaGrafica(switchTC,MSTC,sogliaTC,valSogliaTC);
            }
        });
        MSTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atc.show();
            }
        });
        //Alert per la soglia Frequenza Cardiaca
        final AlertDialog afc=creaAlert("Inserisci soglia Frequenza Cardiaca","Soglia: ","valSogliaFC","Soglia modificata",testoFC,100,sogliaFC);
        switchFC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean("isFC",isChecked).apply();
                modificaGrafica(switchFC,MSFC,sogliaFC,valSogliaFC);
            }
        });
        MSFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afc.show();
            }
        });
        //Alert per la soglia Indice Glicemico
        final AlertDialog aig=creaAlert("Inserisci soglia Indice Glicemico","Soglia: ","valSogliaIG","Soglia modificata",testoIG,110,sogliaIG);
        switchIG.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean("isIG",isChecked).apply();
                modificaGrafica(switchIG,MSIG,sogliaIG,valSogliaIG);
            }
        });
        MSIG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aig.show();
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        sharedPreferences=getSharedPreferences(sharedPrefs,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Log.v("Orario","o: "+ora+":"+min);
        editor.putInt("ora",hourOfDay);
        editor.putInt("minuti",minute);
        editor.putBoolean("isFirstTime",false);
        editor.apply();
        boolean addToday=sharedPreferences.getBoolean("addToday",false);
        Calendar calendar=creaNotificaConAlarm(hourOfDay,minute);
        String t="Orario in cui ricevere la notifica di mancato inserimento del Report  giornaliero: "+ DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
        tw.setText(t);
        //Cancellazione alarm vecchio
        cancellaNotifica();
        //Creazione Alarm per mancato inserimento Report
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent= new Intent(this,AlarmReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1,intent,0);
        if(!addToday){
            if(calendar.before(Calendar.getInstance())){
                calendar.add(Calendar.DATE,1);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
                Toast.makeText(this,"Orario salvato. La notifica arriverà da domani",Toast.LENGTH_SHORT).show();
            }
            else{
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
                Toast.makeText(this,"Orario salvato",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            calendar.add(Calendar.DATE,1);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
            Toast.makeText(this,"Orario salvato. La notifica arriverà da domani",Toast.LENGTH_SHORT).show();
        }
    }
}

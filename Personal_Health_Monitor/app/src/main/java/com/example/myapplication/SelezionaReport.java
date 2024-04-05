package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.myapplication.MainActivity.db;

public class SelezionaReport extends Fragment {


    List<Report> tutti;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SelezionaReport() {
        // Required empty public constructor
    }


    public static SelezionaReport newInstance(String param1, String param2) {
        SelezionaReport fragment = new SelezionaReport();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private class getAllReportAsyncTask extends AsyncTask<Date,Void, List<Report>> {
        @Override
        protected List<Report> doInBackground(Date... Dates) {
                return db.daoReport().tuttiReportConData(Dates[0],Dates[1]);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_seleziona_report, container, false);
        final CalendarView calendario=(CalendarView) view.findViewById(R.id.calendario);
        final Button aggiungi=view.findViewById(R.id.aggiungi);
        aggiungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AggiungiActivity.class);
                startActivity(i);
            }
        });
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar=Calendar.getInstance();
                calendar.set(year,month,dayOfMonth,00,00);
                Date d= calendar.getTime();
                Log.v("Date","d: "+d);
                String data=dayOfMonth+"/"+(month+1)+"/"+year+"";
                calendar.set(year,month,dayOfMonth,23,59);
                Date d1=calendar.getTime();
                try {
                    tutti=new getAllReportAsyncTask().execute(d,d1).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(tutti.size()>1){
                    Intent intent=new Intent(getActivity(),VisualizzaReportConSummary.class);
                    intent.putExtra("data",data);
                    Gson gson=new Gson();
                    String tuttiToString=gson.toJson(tutti);
                    intent.putExtra("report",tuttiToString);
                    startActivity(intent);
                }
                else{
                    Intent intent=new Intent(getActivity(),VisualizzaReport.class);
                    intent.putExtra("data",data);
                    if(tutti.size()==1){
                        Gson gson=new Gson();
                        String tuttiToString=gson.toJson(tutti);
                        intent.putExtra("report",tuttiToString);
                    }
                    startActivity(intent);
                }
            }
        });
        return view;
    }
}

package com.example.myapplication;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@androidx.room.Dao

public interface DaoReport {
        @Insert
        public void InserisciReport(Report report);

        @Delete
        public void EliminaReport(Report report);

        @Query("SELECT avg(pressione_valore) FROM Report WHERE pressione_valore>0 AND data BETWEEN :dataInizio AND :dataFine")
        public float MediaPressione(Date dataInizio,Date dataFine);

        @Query("SELECT avg(temperatura_valore) FROM Report WHERE temperatura_valore>0 AND data BETWEEN :dataInizio AND :dataFine")
        public float MediaTC(Date dataInizio,Date dataFine);

        @Query("SELECT avg(IG_valore) FROM Report WHERE IG_valore>0 AND data BETWEEN :dataInizio AND :dataFine")
        public float MediaIG(Date dataInizio,Date dataFine);

        @Query("SELECT avg(peso_valore) FROM Report WHERE peso_valore>0 AND data BETWEEN :dataInizio AND :dataFine")
        public float MediaPeso(Date dataInizio,Date dataFine);

        @Query("SELECT avg(frequenza_valore) FROM Report WHERE frequenza_valore>0 AND data BETWEEN :dataInizio AND :dataFine")
        public float MediaFrequenza(Date dataInizio,Date dataFine);

        @Query("SELECT avg(OS_valore) FROM Report WHERE OS_valore>0 AND data BETWEEN :dataInizio AND :dataFine")
        public float MediaOS(Date dataInizio,Date dataFine);

        @Query("SELECT count(*) FROM Report WHERE data BETWEEN :dataInizio AND :dataFine")
        public int NumReportInseriti(Date dataInizio,Date dataFine);

        @Query("SELECT * FROM Report WHERE data BETWEEN :dataInizio AND :dataFine AND( pressione_importanza>=:imp OR temperatura_importanza>=:imp OR frequenza_importanza>=:imp OR IG_importanza>=:imp OR peso_importanza>=:imp OR OS_importanza>=:imp)")
        public List<Report> filtraImp(Date dataInizio, Date dataFine,int imp);

        @Query("SELECT * FROM Report WHERE data BETWEEN :dataInizio AND :dataFine AND pressione_valore>0")
        public List<Report> filtraPressione(Date dataInizio, Date dataFine);

        @Query("SELECT * FROM Report WHERE data BETWEEN :dataInizio AND :dataFine AND temperatura_valore>0")
        public List<Report> filtraTemp(Date dataInizio, Date dataFine);

        @Query("SELECT * FROM Report WHERE data BETWEEN :dataInizio AND :dataFine AND IG_valore>0")
        public List<Report> filtraIG(Date dataInizio, Date dataFine);

        @Query("SELECT * FROM Report WHERE data BETWEEN :dataInizio AND :dataFine AND peso_valore>0")
        public List<Report> filtraPeso(Date dataInizio, Date dataFine);

        @Query("SELECT * FROM Report WHERE data BETWEEN :dataInizio AND :dataFine AND frequenza_valore>0")
        public List<Report> filtraFC(Date dataInizio, Date dataFine);

        @Query("SELECT * FROM Report WHERE data BETWEEN :dataInizio AND :dataFine AND OS_valore>0")
        public List<Report> filtraOS(Date dataInizio, Date dataFine);

        @Query("UPDATE Report SET pressione_valore=:valore , pressione_nome=\"Pressione\" , pressione_importanza=5 WHERE id=:id")
        public void AggiornaPress(double valore,int id);

        @Query("UPDATE Report SET temperatura_valore=:valore , temperatura_nome=\"Temperatura Corporea\" , temperatura_importanza=5 WHERE id=:id")
        public void AggiornaTemp(double valore,int id);

        @Query("UPDATE Report SET frequenza_valore=:valore , frequenza_nome=\"Frequenza Cardiaca\" , frequenza_importanza=4 WHERE id=:id")
        public void AggiornaFreq(double valore,int id);

        @Query("UPDATE Report SET IG_valore=:valore , IG_nome=\"Indice Glicemico\" , IG_importanza=3 WHERE id=:id")
        public void AggiornaIG(double valore,int id);

        @Query("UPDATE Report SET peso_valore=:valore , peso_nome=\"Peso\" , peso_importanza=2 WHERE id=:id")
        public void AggiornaPeso(double valore,int id);

        @Query("UPDATE Report SET OS_valore=:valore , OS_nome=\"Ossigenazione sangue\" , OS_importanza=1 WHERE id=:id")
        public void AggiornaOS(double valore,int id);

        @Query("UPDATE Report SET note=:n WHERE id=:id")
        public void AggiornaNote(String n,int id);

        @Query("SELECT * FROM Report")
        public List<Report> tuttiReport();

        @Query("SELECT * FROM Report WHERE  data BETWEEN :d AND :d1")
        public List<Report> tuttiReportConData(Date d,Date d1);

}

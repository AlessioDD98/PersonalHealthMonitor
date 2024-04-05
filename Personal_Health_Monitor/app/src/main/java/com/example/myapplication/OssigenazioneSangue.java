package com.example.myapplication;

import android.system.Os;

import androidx.room.TypeConverter;

public class OssigenazioneSangue {

    String nome;
    double valore;
    int importanza;

    public OssigenazioneSangue(double valore) {
        this.nome = "Ossigenazione sangue";
        this.valore = valore;
        this.importanza = 1;
    }

    @TypeConverter
    public static OssigenazioneSangue toOssigenazioneSangue(double valore){
        return valore < 0 ? null: new OssigenazioneSangue(valore);
    }

    @TypeConverter
    public static double fromOssigenazioneSangue(OssigenazioneSangue ossigenazioneSangue){
        return ossigenazioneSangue.getValore() < 0 ? -1 : ossigenazioneSangue.getValore();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValore() {
        return valore;
    }

    public void setValore(double valore) {
        this.valore = valore;
    }

    public int getImportanza() {
        return importanza;
    }

    public void setImportanza(int importanza) {
        this.importanza = importanza;
    }
}

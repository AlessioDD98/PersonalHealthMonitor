package com.example.myapplication;

import androidx.room.TypeConverter;


public class TemperaturaCorporea {
    String nome;
    double valore;
    int importanza;

    public TemperaturaCorporea(double valore) {
        this.nome = "Temperatura Corporea";
        this.valore = valore;
        this.importanza = 5;
    }

    @TypeConverter
    public static TemperaturaCorporea toTemperaturaCorporea(double valore){
        return valore < 0 ? null: new TemperaturaCorporea(valore);
    }

    @TypeConverter
    public static double fromTemperaturaCorporea(TemperaturaCorporea temperaturaCorporea){
        return temperaturaCorporea.getValore() < 0 ? -1 : temperaturaCorporea.getValore();
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

package com.example.myapplication;

import androidx.room.TypeConverter;

public class Peso {

    String nome;
    double valore;
    int importanza;

    public Peso(double valore) {
        this.nome = "Peso";
        this.valore = valore;
        this.importanza = 2;
    }

    @TypeConverter
    public static Peso toPeso(double valore){
        return valore < 0 ? null: new Peso(valore);
    }

    @TypeConverter
    public static double fromPeso(Peso peso){
        return peso.getValore() < 0 ? -1 : peso.getValore();
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

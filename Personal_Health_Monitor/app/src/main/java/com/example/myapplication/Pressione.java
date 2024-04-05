package com.example.myapplication;

import androidx.room.TypeConverter;

public class Pressione {

    String nome;
    double valore;
    int importanza;

    public Pressione(double valore) {
        this.nome = "Pressione";
        this.valore = valore;
        this.importanza = 5;
    }

    @TypeConverter
    public static Pressione toPressione(double valore){
        return valore < 0 ? null: new Pressione(valore);
    }

    @TypeConverter
    public static double fromPressione(Pressione pressione){
        return pressione.getValore() < 0 ? -1 : pressione.getValore();
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

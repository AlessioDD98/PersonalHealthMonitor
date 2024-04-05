package com.example.myapplication;

import androidx.room.TypeConverter;

public class FrequenzaCardiaca {

    String nome;
    double valore;
    int importanza;

    public FrequenzaCardiaca(double valore) {
        this.nome = "Frequenza Cardiaca";
        this.valore = valore;
        this.importanza = 4;
    }

    @TypeConverter
    public static FrequenzaCardiaca toFrequenzaCardiaca(double valore){
        return valore < 0 ? null: new FrequenzaCardiaca(valore);
    }

    @TypeConverter
    public static double fromFrequenzaCardiaca(FrequenzaCardiaca frequenzaCardiaca){
        return frequenzaCardiaca.getValore() < 0 ? -1 : frequenzaCardiaca.getValore();
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

package com.example.myapplication;

import androidx.room.TypeConverter;

public class IndiceGlicemico {

    String nome;
    double valore;
    int importanza;

    public IndiceGlicemico(double valore) {
        this.nome = "Indice Glicemico";
        this.valore = valore;
        this.importanza = 3;
    }

    @TypeConverter
    public static IndiceGlicemico toIndiceGlicemico(double valore){
        return valore < 0 ? null: new IndiceGlicemico(valore);
    }

    @TypeConverter
    public static double fromIndiceGlicemico(IndiceGlicemico indiceGlicemico){
        return indiceGlicemico.getValore() < 0 ? -1 : indiceGlicemico.getValore();
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

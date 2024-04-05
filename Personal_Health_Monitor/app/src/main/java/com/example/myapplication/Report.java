package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity
public class Report implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @TypeConverters(DateConverter.class)
    @NonNull
    private Date data;
    @Embedded(prefix = "pressione_")
    //@TypeConverters(Pressione.class)
    private Pressione pressione;
    @Embedded(prefix = "temperatura_")
    //@TypeConverters(TemperaturaCorporea.class)
    private TemperaturaCorporea temperaturaCorporea;
    @Embedded(prefix = "IG_")
    //@TypeConverters(IndiceGlicemico.class)
    private IndiceGlicemico indiceGlicemico;
    @Embedded(prefix = "frequenza_")
    //@TypeConverters(FrequenzaCardiaca.class)
    private FrequenzaCardiaca frequenzaCardiaca;
    @Embedded(prefix = "peso_")
    //@TypeConverters(Peso.class)
    private Peso peso;
    @Embedded(prefix = "OS_")
    //@TypeConverters(OssigenazioneSangue.class)
    private OssigenazioneSangue ossigenazioneSangue;
    private String Note;

    public Report(){}

    public Report(int id, Date data, Pressione pressione, TemperaturaCorporea temperaturaCorporea, IndiceGlicemico indiceGlicemico, FrequenzaCardiaca frequenzaCardiaca,
                  Peso peso, OssigenazioneSangue ossigenazioneSangue, String Note){
        this.id=id;
        this.data=data;
        this.pressione=pressione;
        this.temperaturaCorporea=temperaturaCorporea;
        this.indiceGlicemico=indiceGlicemico;
        this.frequenzaCardiaca=frequenzaCardiaca;
        this.peso=peso;
        this.ossigenazioneSangue=ossigenazioneSangue;
        this.Note=Note;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(Note);
    }

    protected Report(Parcel in) {
        id = in.readInt();
        Note = in.readString();
    }

    public static final Creator<Report> CREATOR = new Creator<Report>() {
        @Override
        public Report createFromParcel(Parcel in) {
            return new Report(in);
        }

        @Override
        public Report[] newArray(int size) {
            return new Report[size];
        }
    };

    public Pressione getPressione() {
        return pressione;
    }

    public void setPressione(Pressione pressione) {
        this.pressione = pressione;
    }

    public TemperaturaCorporea getTemperaturaCorporea() {
        return temperaturaCorporea;
    }

    public void setTemperaturaCorporea(TemperaturaCorporea temperaturaCorporea) {
        this.temperaturaCorporea = temperaturaCorporea;
    }

    public IndiceGlicemico getIndiceGlicemico() {
        return indiceGlicemico;
    }

    public void setIndiceGlicemico(IndiceGlicemico indiceGlicemico) {
        this.indiceGlicemico = indiceGlicemico;
    }

    public FrequenzaCardiaca getFrequenzaCardiaca() {
        return frequenzaCardiaca;
    }

    public void setFrequenzaCardiaca(FrequenzaCardiaca frequenzaCardiaca) {
        this.frequenzaCardiaca = frequenzaCardiaca;
    }

    public Peso getPeso() {
        return peso;
    }

    public void setPeso(Peso peso) {
        this.peso = peso;
    }

    public OssigenazioneSangue getOssigenazioneSangue() {
        return ossigenazioneSangue;
    }

    public void setOssigenazioneSangue(OssigenazioneSangue ossigenazioneSangue) {
        this.ossigenazioneSangue = ossigenazioneSangue;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

package com.example.imenikapp.db.model;

import android.support.constraint.ConstraintLayout;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = BrojTelefona.TABLE_NAME_BROJ)
public class BrojTelefona {
    public static final String TABLE_NAME_BROJ="brojevi";
    public static final String FIELD_NAME_KUCNI="kucni";
    public static final String FIELD_NAME_POSLOVNI="poslovni";
    public static final String FIELD_NAME_MOBILNI="mobilni";
    public static final String FIELD_NAME_KONTAKT="kontakt";
    public static final String FIELD_NAME_ID="id";


    @DatabaseField (columnName = FIELD_NAME_ID,generatedId = true)
    private int id;
    @DatabaseField(columnName = FIELD_NAME_POSLOVNI)
    private String poslovni;
    @DatabaseField(columnName = FIELD_NAME_MOBILNI)
    private String mobilni;
    @DatabaseField(columnName = FIELD_NAME_KUCNI)
    private String kucni;
    @DatabaseField(columnName = FIELD_NAME_KONTAKT,foreign = true,foreignAutoRefresh = true)
    private Kontakt kontakt;

    public BrojTelefona() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoslovni() {
        return poslovni;
    }

    public void setPoslovni(String poslovni) {
        this.poslovni = poslovni;
    }

    public String getMobilni() {
        return mobilni;
    }

    public void setMobilni(String mobilni) {
        this.mobilni = mobilni;
    }

    public String getKucni() {
        return kucni;
    }

    public void setKucni(String kucni) {
        this.kucni = kucni;
    }

    public Kontakt getKontakt() {
        return kontakt;
    }

    public void setKontakt(Kontakt kontakt) {
        this.kontakt = kontakt;
    }
}

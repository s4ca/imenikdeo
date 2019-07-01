package com.example.imenikapp.db.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = Kontakt.TABLE_NAME_KONTAKT)
public class Kontakt {

    public static final String TABLE_NAME_KONTAKT="kontakti";
    public static final String FIELD_NAME_SLIKA="slike";
public static final String FIELD_NAME_ID="ids";
public static final String FIELD_NAME_IME="imena";
public static final String FIELD_NAME_PREZIME="prezimena";
public static final String FIELD_NAME_ADRESA="adrese";
public static final String FIELD_NAME_BROJ="brojevi";

@DatabaseField(columnName = FIELD_NAME_ID,generatedId = true)
    private int id;
@DatabaseField(columnName = FIELD_NAME_SLIKA)
private String slika;

@DatabaseField(columnName = FIELD_NAME_IME)
    private String ime;
@DatabaseField(columnName = FIELD_NAME_PREZIME)
    private String prezime;
@DatabaseField(columnName = FIELD_NAME_ADRESA)
    private String adresa;

@ForeignCollectionField(columnName = FIELD_NAME_BROJ,eager = true)
    private ForeignCollection<BrojTelefona>brojeviTelefona;

    public Kontakt() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public ForeignCollection<BrojTelefona> getBrojeviTelefona() {
        return brojeviTelefona;
    }

    public void setBrojeviTelefona(ForeignCollection<BrojTelefona> brojeviTelefona) {
        this.brojeviTelefona = brojeviTelefona;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }
}

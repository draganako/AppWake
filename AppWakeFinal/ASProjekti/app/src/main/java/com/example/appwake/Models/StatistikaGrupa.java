package com.example.appwake.Models;

import java.util.Date;

public class StatistikaGrupa {

    private double prosekProbudjeni;
    private int ukupnoProbudjeni;
    private int najcescePrviId;
    private String najcescePrviUsername;
    private int brojPrvihPrvi;
    private int najviseClanova;
    private Date najviseClanovaDatum;

    public StatistikaGrupa(){}

    public StatistikaGrupa(double prosek, int ukupno, int prvi, String prviUsername, int kolikoPrvi, int najviseClanova, Date kad){
        this.prosekProbudjeni = prosek;
        this.ukupnoProbudjeni = ukupno;
        this.najcescePrviId = prvi;
        this.najcescePrviUsername = prviUsername;
        this.brojPrvihPrvi = kolikoPrvi;
        this.najviseClanova = najviseClanova;
        this.najviseClanovaDatum = kad;
    }

    public double getProsekProbudjeni() {
        return prosekProbudjeni;
    }

    public void setProsekProbudjeni(double prosekProbudjeni) {
        this.prosekProbudjeni = prosekProbudjeni;
    }

    public int getUkupnoProbudjeni() {
        return ukupnoProbudjeni;
    }

    public void setUkupnoProbudjeni(int ukupnoProbudjeni) {
        this.ukupnoProbudjeni = ukupnoProbudjeni;
    }

    public int getNajcescePrviId() {
        return najcescePrviId;
    }

    public void setNajcescePrviId(int najcescePrviId) {
        this.najcescePrviId = najcescePrviId;
    }

    public int getBrojPrvihPrvi() {
        return brojPrvihPrvi;
    }

    public void setBrojPrvihPrvi(int brojPrvihPrvi) {
        this.brojPrvihPrvi = brojPrvihPrvi;
    }

    public int getNajviseClanova() {
        return najviseClanova;
    }

    public void setNajviseClanova(int najviseClanova) {
        this.najviseClanova = najviseClanova;
    }

    public Date getNajviseClanovaDatum() {
        return najviseClanovaDatum;
    }

    public void setNajviseClanovaDatum(Date najviseClanovaDatum) {
        this.najviseClanovaDatum = najviseClanovaDatum;
    }

    public String getNajcescePrviUsername() {
        return najcescePrviUsername;
    }

    public void setNajcescePrviUsername(String najcescePrviUsername) {
        this.najcescePrviUsername = najcescePrviUsername;
    }
}


/*
* uspesan upit:
* {
    "prosekProbudjeni": 0.725,
    "ukupnoProbudjeni": 22,
    "najcescePrviId": 13,
    "brojPrvihPrvi": 2,
    "najviseClanova": 10,
    "najviseClanovaDatum": "2005-05-09 10:15:22"
}
* neuspesan upit (nepostojeca grupa):
*
* {
    "prosekProbudjeni": 0,
    "ukupnoProbudjeni": 0,
    "najcescePrviId": 0,
    "brojPrvihPrvi": 0,
    "najviseClanova": 0,
    "najviseClanovaDatum": null
}
*
* exception: ----
* vraca response -1??
* */
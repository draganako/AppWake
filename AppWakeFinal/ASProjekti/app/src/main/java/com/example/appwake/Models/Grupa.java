package com.example.appwake.Models;

import java.util.Date;
import java.util.List;

public class Grupa {

    private String naziv;
    private int id;
    private int idAdmina;
    private Date datumKreiranja;//ekvivalentno sa DateTime u C#-u
    private int brojClanova;
    private Date zeljenoVremeBudjenja;
    private Korisnik jeAdmin;
    private List<JeClan> clanovi;
    private List<Statistika> statistika;

    private StatistikaGrupa zaPrikaz;

    public Grupa(int id, String naziv) {
        this.id = id;
        this.naziv = naziv;
    }

    public Grupa() {

    }


    public void setNaziv(String naziv)
    {
        this.naziv = naziv;
    }

    public String getNaziv()
    {
        return this.naziv;
    }

    public void setIdAdmina(int id)
    {
        this.idAdmina = id;
    }

    public int getIdAdmina()
    {
        return this.idAdmina;
    }

    public  Date getDatumKreiranja() { return this.datumKreiranja; }
    public  void setDatumKreiranja(Date datumKreiranja) {this.datumKreiranja=datumKreiranja; }

    public  int getBrojClanova() { return brojClanova; }
    public  void setBrojClanova(int brojClanova) { this.brojClanova=brojClanova; }

    public Date getZeljenoVremeBudjenja() { return zeljenoVremeBudjenja; }
    public void setZeljenoVremeBudjenja(Date zeljenoVremeBudjenja) { this.zeljenoVremeBudjenja=zeljenoVremeBudjenja; }


    public void setJeAdmin(Korisnik jeAdmin) { this.jeAdmin=jeAdmin;}
    public Korisnik getJeAdmin() { return this.jeAdmin;}

    public List<JeClan> getClanovi() { return clanovi;}
    public void setClanovi(List<JeClan> clanovi) {this.clanovi=clanovi;}

    public List<Statistika> getStatistika() { return statistika; }
    public void setStatistika(List<Statistika> statistika) { this.statistika=statistika; }

    public Grupa(String naziv)
    {
        this.naziv = naziv;
       // clanovi = new List<JeClan>();
       // statistika = new List<Statistika>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StatistikaGrupa getZaPrikaz() {
        return zaPrikaz;
    }

    public void setZaPrikaz(StatistikaGrupa zaPrikaz) {
        this.zaPrikaz = zaPrikaz;
    }
}

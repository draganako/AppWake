package com.example.appwake.Models;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Korisnik {

    private int id;
    private String ime;
    private String prezime;
    private String username;
    private Date datumRodjenja;
    private String email;
    private String password;
    private int jeAdmin;
    private String slika;
    private Date realnoVremeBudjenja;
    private int status;

    private ArrayList<Grupa> grupe;

    protected JSONObject korisnikJSON;

    private static Korisnik instance;

    public static Korisnik getInstance(){return  instance;}
    public static void setInstance(Korisnik k){instance=k;}

    public Korisnik(){
        this.id = -1;
        this.ime = "";
        this.prezime = "";
        this.datumRodjenja = null;
        this.jeAdmin = 0;
        this.slika = "boy";
        this.status = 0; //sta je inicijalno status?
        this.grupe = new ArrayList<>();
    }
    public Korisnik(int id, String un){
        this.id=id;
        username=un;
    }

    public Korisnik(int id, String ime, String prezime, String username, Date datumRodjenja,String email, String pass, int admin, String slika, int status)
    {
        if(this.instance!=null)
            setInstance(null);
        this.instance=new Korisnik();
        instance.id=id;
        instance.ime=ime;
        instance.prezime=prezime;
        instance.username=username;
        instance.email=email;
        instance.password=pass;
        instance.datumRodjenja=datumRodjenja;
        instance.jeAdmin=admin;
        instance.slika = slika;
        instance.status = status;
        this.grupe = new ArrayList<>();

        instance.korisnikJSON=new JSONObject();
        JSONObject fax=new JSONObject();
        try {

            instance.korisnikJSON.put("Id", id);
            instance.korisnikJSON.put("Ime", ime);
            instance.korisnikJSON.put("Prezime", prezime);
            instance.korisnikJSON.put("Username", username);
            instance.korisnikJSON.put("Password", pass);
            instance.korisnikJSON.put("Email", email);
            instance.korisnikJSON.put("JeAdministrator",admin);
            instance.korisnikJSON.put("Status", status);
            instance.korisnikJSON.put("Slika", slika);
        }
        catch (Exception e)
        {

        }
    }
    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getIme()
    {
        return this.ime;
    }

    public void setIme(String ime)
    {
        this.ime = ime;
    }


    public String getPrezime() {
        return this.prezime ;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String toString()
    {
        return "Name " + ime + " prezime "+ prezime + " id " + id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date dr) {
        this.datumRodjenja = dr;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String dr) {
        this.password = dr;
    }
    public int getJeAdmin() {
        return jeAdmin;
    }

    public void setJeAdmin(int dr) {
        this.jeAdmin = dr;
    }
    public Korisnik(String username){ //dodati i ostalo
        this.username = username;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Grupa> getGrupe() {
        return grupe;
    }

    public void setGrupe(ArrayList<Grupa> grupe) {
        this.grupe = grupe;
    }


    public Date getRealnoVremeBudjenja() {return realnoVremeBudjenja;}
    public void setRealnoVremeBudjenja(Date realnoVremeBudjenja) { this.realnoVremeBudjenja=realnoVremeBudjenja; }
}

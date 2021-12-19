package com.example.appwake.Models;

import java.util.Date;

public class Statistika {


    private int id;
    private Date datum;
    private Date vremeBudjenja;
    private int brojProbudjenih;
    private int brojClanova;

    public Statistika(){}

    public int getId() { return id; }
    public void setId(int id) { this.id=id; }

    public Date getDatum() { return datum; }
    public void setDatum(Date datum) { this.datum=datum; }

    public Date getVremeBudjenja() { return vremeBudjenja; }
    public void getVremeBudjenja(Date vremeBudjenja) { this.vremeBudjenja=vremeBudjenja; }

    public int getBrojProbudjenih() { return brojProbudjenih; }
    public void setBrojProbudjenih(int brojProbudjenih) { this.brojProbudjenih=brojProbudjenih; }

    public int getBrojClanova() { return brojClanova; }
    public void setBrojClanova(int id) { this.brojClanova=brojClanova; }

}

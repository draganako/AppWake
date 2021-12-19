package com.example.appwake.Models;

import java.util.Date;

public class JeClan {

    private int id;
    private Date datumUclanjenja;
    private Date datumNapustanja;
    private Date realnoVremeBudjenja;
    private Korisnik korisnikJeClan;
    private Grupa jeClanGrupa;

    public int getId() {return id;}
    public void setId(int id) { this.id=id; }

    public Date getDatumUclanjenja() {return datumUclanjenja;}
    public void setDatumUclanjenja(Date datumUclanjenja) { this.datumUclanjenja=datumUclanjenja; }

    public Date getDatumNapustanja() {return datumNapustanja;}
    public void setDatumNapustanja(Date datumNapustanja) { this.datumNapustanja=datumNapustanja; }

    public Date getRealnoVremeBudjenja() {return realnoVremeBudjenja;}
    public void setRealnoVremeBudjenja(Date realnoVremeBudjenja) { this.realnoVremeBudjenja=realnoVremeBudjenja; }

    public Korisnik getKorisnikJeClan() {return korisnikJeClan;}
    public void  setKorisnikJeClan(Korisnik korisnikJeClan) { this.korisnikJeClan=korisnikJeClan;}

    public Grupa getJeClanGrupa() {return jeClanGrupa;}
    public void setJeClanGrupa(Grupa g) { jeClanGrupa=g;}

}

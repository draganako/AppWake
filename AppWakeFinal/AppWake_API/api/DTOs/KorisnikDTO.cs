using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using api.Models;

namespace api.DTOs
{
    public class KorisnikDTO
    {
        public virtual int Id { get; set; }
        public virtual string Ime { get; set; }
        public virtual string Prezime { get; set; }
        public virtual DateTime? DatumRodjenja { get; set; }
        public virtual DateTime? RealnoVremeBudjenja { get; set; }
        public virtual string Username { get; set; }
        public virtual string Password { get; set; }
        public virtual string Salt { get; set; }
        public virtual string Email { get; set; }
        public virtual int JeAdministrator { get; set; }
        public virtual string Slika { get; set; }
        public virtual int Status { get; set; }

        public KorisnikDTO(Korisnik korisnik)
        {
            this.Id = korisnik.Id;
            this.Ime = korisnik.Ime;
            this.Prezime = korisnik.Prezime;
            this.DatumRodjenja = korisnik.DatumRodjenja;
            this.RealnoVremeBudjenja = korisnik.RealnoVremeBudjenja;
            this.Username = korisnik.Username;
            this.Password = korisnik.Password;
            this.Salt = korisnik.Salt;
            this.Email = korisnik.Email;
            this.JeAdministrator = korisnik.JeAdministrator;
            this.Slika = korisnik.Slika;
            this.Status = korisnik.Status;
        }

        public KorisnikDTO()
        {

        }

    }

    public class KorisnikDetailDTO //???
    {
        public virtual int Id { get; set; }
        public virtual string Ime { get; set; }
        public virtual string Prezime { get; set; }
        public virtual DateTime? DatumRodjenja { get; set; } 
        public virtual string Email { get; set; }
        public virtual string Username { get; set; }
        public virtual string Password { get; set; } 
        public virtual int JeAdministrator { get; set; } 
        public virtual List<int> IdGrupe { get; set; }
        public virtual List<int> IdStatistike { get; set; }
        public virtual List<int> IdGrupePrvi { get; set; }

        public KorisnikDetailDTO() { }

        public KorisnikDetailDTO(Korisnik korisnik)
        {
            this.Id = korisnik.Id;
            this.Ime = korisnik.Ime;
            this.Prezime = korisnik.Prezime;
            this.DatumRodjenja = korisnik.DatumRodjenja;
            this.Username = korisnik.Username;
            this.Password = korisnik.Password;
            this.Email = korisnik.Email;
            this.JeAdministrator = korisnik.JeAdministrator;
            foreach(JeClan jc in korisnik.Grupe)
            {
                IdGrupe.Add(jc.JeClanGrupa.Id);
                if (jc.JeClanGrupa.JeAdmin.Id == Id)
                    IdGrupePrvi.Add(jc.JeClanGrupa.Id);
            }
            foreach(Statistika s in korisnik.StatistikePrvi)
            {
                IdGrupePrvi.Add(s.PripadaGrupi.Id);
            }
        }
    }
}
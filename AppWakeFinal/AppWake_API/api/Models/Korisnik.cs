using api.DTOs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace api.Models
{
    public class Korisnik
    {
        public virtual int Id { get; set; }
        public virtual string Ime { get; set; }
        public virtual string Prezime { get; set; }
        public virtual DateTime? DatumRodjenja { get; set; } //u bazi kao string 
        public virtual DateTime? RealnoVremeBudjenja { get; set; }
        public virtual string Email { get; set; }
        public virtual string Username { get; set; }
        public virtual string Password { get; set; } 
        public virtual string Salt { get; set; }
        public virtual int JeAdministrator { get; set; }
        public virtual string Slika { get; set; }
        public virtual int Status { get; set; }
        public virtual IList<JeClan> Grupe { get; set; }

        ////admin grupama
        public virtual IList<Grupa> GrupeAdmin { get; set; } //////veza many to one

        ////prvi u grupama
        public virtual IList<Statistika> StatistikePrvi { get; set; }

        public Korisnik()
        {
            Grupe = new List<JeClan>();
            GrupeAdmin = new List<Grupa>();
            StatistikePrvi = new List<Statistika>();
        }

        public Korisnik(KorisnikDTO korisnikDTO)
            :this()
        {
            Id = korisnikDTO.Id;
            Ime = korisnikDTO.Ime;
            Prezime = korisnikDTO.Prezime;
            DatumRodjenja = korisnikDTO.DatumRodjenja;
            RealnoVremeBudjenja = korisnikDTO.RealnoVremeBudjenja;
            Email = korisnikDTO.Email;
            Username = korisnikDTO.Username;
            Password = korisnikDTO.Password;
            Salt = korisnikDTO.Salt;
            JeAdministrator = korisnikDTO.JeAdministrator;
            Slika = korisnikDTO.Slika;
            Status = korisnikDTO.Status;
        }
    }

    //public class Administrator : Korisnik
    //{
    //}
}
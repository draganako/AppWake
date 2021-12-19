using api.DataProviders;
using api.DTOs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace api.Models
{
    public class Grupa
    {
        public virtual int Id { get; set; }
        public virtual string Naziv { get; set; }
        public virtual DateTime DatumKreiranja { get; set; }
        public virtual int BrojClanova { get; set; }
        public virtual DateTime? ZeljenoVremeBudjenja { get; set; } ///////ovde datetime, a u bazi string
        //trebalo bi da se stavi nullable, jer ne mora da bude setovan!!! -> baca izuzetak pri upisu u bazu(ali izvrsi upis)

        public virtual Korisnik JeAdmin { get; set; } 

        //public virtual Korisnik JePrvi { get; set; } 
        public virtual IList<JeClan> Clanovi { get; set; }

        //statistike grupe
        public virtual IList<Statistika> Statistike { get; set; }

        public Grupa()
        {
            Clanovi = new List<JeClan>();
            Statistike = new List<Statistika>();
        }

        public Grupa(GrupaDTO grupaDTO)
            :this()
        {
            Id = grupaDTO.Id;
            Naziv = grupaDTO.Naziv;
            DatumKreiranja = grupaDTO.DatumKreiranja;
            BrojClanova = grupaDTO.BrojClanova;
            ZeljenoVremeBudjenja = grupaDTO.ZeljenoVremeBudjenja;
            //JeAdmin - izbacen odavde
        }



        /*
        //nece ovako da bude za clanove!
        //niz korisnika clanova
        public virtual IList<Korisnik> Clanovi { get; set; }

        public virtual IList<JeClan> JeClanKorisnici { get; set; }

        
        */
    }
}
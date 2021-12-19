using api.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace api.DTOs
{
    public class GrupaDTO
    {
        public virtual int Id { get; set; }
        public virtual string Naziv { get; set; }
        public virtual DateTime DatumKreiranja { get; set; }
        public virtual int BrojClanova { get; set; }
        public virtual DateTime? ZeljenoVremeBudjenja { get; set; }
        public virtual int IdAdmina { get; set; }

        public GrupaDTO(Grupa grupa)
        {
            this.Id = grupa.Id;
            this.Naziv = grupa.Naziv;
            this.DatumKreiranja = grupa.DatumKreiranja;
            this.BrojClanova = grupa.BrojClanova;
            this.ZeljenoVremeBudjenja = grupa.ZeljenoVremeBudjenja;
            this.IdAdmina = grupa.JeAdmin.Id;
        }

        public GrupaDTO()
        {

        }
    }
}
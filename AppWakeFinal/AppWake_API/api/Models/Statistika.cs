using api.DTOs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace api.Models
{
    public class Statistika
    {
        public virtual int Id { get; set; }
        public virtual DateTime Datum { get; set; } //cuva i vreme i datum budjenja
        public virtual int BrojProbudjenih { get; set; }
        public virtual int BrojClanova { get; set; }

        public virtual Korisnik Prvi { get; set; } // nije uvezano na slici!!! - pamtimo u grupi, ili bolje ovde?
        public virtual Grupa PripadaGrupi { get; set; }

        public Statistika() { }

        public Statistika(StatistikaDTO statistikaDTO)
        {
            Id = statistikaDTO.Id;
            Datum = statistikaDTO.Datum;
            BrojProbudjenih = statistikaDTO.BrojProbudjenih;

            //Prvi i PripadaGrupi - u Update i AddStatistika
        }

    }
}
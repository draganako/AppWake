using api.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace api.DTOs
{
    public class StatistikaDTO
    {
        public virtual int Id { get; set; }
        public virtual DateTime Datum { get; set; }
        public virtual int BrojProbudjenih { get; set; }
        public virtual int BrojClanova { get; set; }
        public virtual int IdPrvog { get; set; }
        public virtual int IdGrupe { get; set; }

        //public virtual string ImePrvog { get; set; }
        //public virtual string NazivGrupe { get; set; }

        public StatistikaDTO(Statistika st)
        {
            this.Id = st.Id;
            this.Datum = st.Datum;
            this.BrojProbudjenih = st.BrojProbudjenih;
            this.BrojClanova = st.BrojClanova;
            this.IdPrvog = st.Prvi.Id;
            this.IdGrupe = st.PripadaGrupi.Id;
            //this.ImePrvog = st.Prvi.Ime + " " + st.Prvi.Prezime;
            //this.NazivGrupe = st.PripadaGrupi.Naziv;
        }

        public StatistikaDTO()
        {

        }
    }
}
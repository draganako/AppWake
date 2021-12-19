using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace api.Models
{
    public class JeClan
    {
        public JeClan() { }

        public JeClan(DateTime datumUclanjenja, DateTime? datumNapustanja)
        {
            DatumUclanjenja = datumUclanjenja;
            DatumNapustanja = datumNapustanja;
        }

        public virtual int Id { get; set; }
        public virtual DateTime DatumUclanjenja { get; set; }
        public virtual DateTime? DatumNapustanja { get; set; }
        public virtual Korisnik KorisnikJeClan { get; set; }
        public virtual Grupa JeClanGrupa { get; set; }
    }
}
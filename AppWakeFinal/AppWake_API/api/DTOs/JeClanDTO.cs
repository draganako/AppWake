using api.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace api.DTOs
{
    public class JeClanDTO
    {
        public virtual int Id { get; set; } 
        public virtual DateTime DatumUclanjenja { get; set; }  
        public virtual DateTime? DatumNapustanja { get; set; }
        public virtual int IdKorisnika { get; set; } 
        public virtual int IdGrupe { get; set; }

        public JeClanDTO(JeClan cl)
        {
            Id = cl.Id;
            DatumUclanjenja = cl.DatumUclanjenja;
            DatumNapustanja = cl.DatumNapustanja;
            IdKorisnika = cl.KorisnikJeClan.Id;
            IdGrupe = cl.JeClanGrupa.Id;
        }

        public JeClanDTO()
        {
        }
    }
}
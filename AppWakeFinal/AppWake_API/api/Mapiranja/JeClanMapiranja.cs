using api.Models;
using FluentNHibernate.Mapping;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace api.Mapiranja
{
    public class JeClanMapiranja : ClassMap<JeClan> 
    {
        public JeClanMapiranja()
        {
            Table("JE_CLAN");

            Id(x => x.Id).Column("ID_JE_CLAN").GeneratedBy.Native();

            Map(x => x.DatumUclanjenja).Column("DATUM_UCLANJENJA");
            Map(x => x.DatumNapustanja).Column("DATUM_NAPUSTANJA");

            References(x => x.KorisnikJeClan).Column("ID_KORISNIKA");
            References(x => x.JeClanGrupa).Column("ID_GRUPE");
        }
    }
}
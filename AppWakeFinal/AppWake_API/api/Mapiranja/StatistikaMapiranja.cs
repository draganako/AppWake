using api.Models;
using FluentNHibernate.Mapping;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace api.Mapiranja
{
    public class StatistikaMapiranja : ClassMap<Statistika>
    {
        public StatistikaMapiranja()
        {
            Table("STATISTIKA");

            Id(x => x.Id).Column("ID_STATISTIKA").GeneratedBy.Native();

            Map(x => x.Datum).Column("DATUM");
            Map(x => x.BrojProbudjenih).Column("BROJ_PROBUDJENIH");
            Map(x => x.BrojClanova).Column("BROJ_CLANOVA");

            References(x => x.Prvi).Column("ID_PRVOG").LazyLoad();
            References(x => x.PripadaGrupi).Column("ID_GRUPE").LazyLoad();

        }
    }
}
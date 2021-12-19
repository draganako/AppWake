using api.Models;
using FluentNHibernate.Mapping;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace api.Mapiranja
{
    public class GrupaMapiranja : ClassMap<Grupa>
    {
        public GrupaMapiranja()
        {
            Table("GRUPA");

            Id(x => x.Id).Column("ID_GRUPA").GeneratedBy.Native(); //ili identity?

            Map(x => x.Naziv).Column("NAZIV");
            Map(x => x.DatumKreiranja).Column("DATUM_KREIRANJA");
            Map(x => x.BrojClanova).Column("BROJ_CLANOVA");
            Map(x => x.ZeljenoVremeBudjenja).Column("ZELJENO_VREME_BUDJENJA");

            References(x => x.JeAdmin).Column("ID_ADMINA");
            //References(x => x.JePrvi).Column("ID_PRVOG");

            HasMany(x => x.Clanovi).KeyColumn("ID_GRUPE").LazyLoad().Cascade.All().Inverse();

            HasMany(x => x.Statistike).KeyColumn("ID_GRUPE").LazyLoad().Cascade.All().Inverse();

        }
    }
}
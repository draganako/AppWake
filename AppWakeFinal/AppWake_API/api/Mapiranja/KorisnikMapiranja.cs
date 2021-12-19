using FluentNHibernate.Mapping;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using api.Models;

namespace api.Mapiranja
{
    public class KorisnikMapiranja : ClassMap<Korisnik>
    {
        public KorisnikMapiranja()
        {
            Table("KORISNIK");

            Id(x => x.Id).Column("ID_KORISNIK").GeneratedBy.Native();

            //DiscriminateSubClassesOnColumn("JE_ADMINISTRATOR", 0); //0 je podrazumevana vrednost, a za admina ovaj flag ima vrednost 1

            Map(x => x.Ime).Column("IME");
            Map(x => x.Prezime).Column("PREZIME");
            Map(x => x.DatumRodjenja).Column("DATUM_RODJENJA");
            Map(x => x.RealnoVremeBudjenja).Column("REALNO_VREME_BUDJENJA");
            Map(x => x.Username).Column("USERNAME");
            Map(x => x.Password).Column("PASSWORD");
            Map(x => x.Salt).Column("SALT");
            Map(x => x.Email).Column("EMAIL");
            Map(x => x.JeAdministrator).Column("JE_ADMINISTRATOR");
            Map(x => x.Slika).Column("SLIKA");
            Map(x => x.Status).Column("STATUS");

            HasMany(x => x.Grupe).KeyColumn("ID_KORISNIKA").LazyLoad().Cascade.All().Inverse();

            HasMany(x => x.GrupeAdmin).KeyColumn("ID_ADMINA").LazyLoad().Cascade.All().Inverse();

            HasMany(x => x.StatistikePrvi).KeyColumn("ID_PRVOG").LazyLoad().Cascade.All().Inverse();

        }
    }

    //public class AdministratorMapiranja : SubclassMap<Administrator>
    //{
    //    public AdministratorMapiranja()
    //    {
    //        DiscriminatorValue(1);
    //    }
    //}
}
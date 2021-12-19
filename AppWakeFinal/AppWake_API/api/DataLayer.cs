using System;
using FluentNHibernate.Cfg;
using FluentNHibernate.Cfg.Db;
using NHibernate;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using api.Mapiranja;

namespace api
{
    public class DataLayer
    {
        private static ISessionFactory _factory = null;
        private static object objLock = new object();

        //funkcija za otvaranje sesije:
        public static ISession GetSession()
        {
            //ukoliko sessionFactory nije kreiran
            if (_factory == null)
            {
                lock (objLock)
                {
                    if (_factory == null)
                    {
                        _factory = CreateSessionFactory();
                    }
                }
            }
            return _factory.OpenSession();
        }


        //konfiguracija session factory-ja:
        private static ISessionFactory CreateSessionFactory()
        {
            try
            {
                var cfg = MySQLConfiguration.Standard
                    .ConnectionString(c => c.Is("server=188.121.44.164;user=appwake;charset=utf8;database=appwake;port=3306;password=staWARSrw@rs7;"));

                return Fluently.Configure()
                    .Database(cfg.ShowSql())
                    .Mappings(m => m.FluentMappings.AddFromAssemblyOf<KorisnikMapiranja>())
                    //.Conventions.Add(FluentNHibernate.Conventions.Helpers.DefaultLazy.Never())
                    .BuildSessionFactory();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                //Console.WriteLine(ex.StackTrace);
                Console.WriteLine(ex.InnerException);

                return null;
            }
        }

    }
}
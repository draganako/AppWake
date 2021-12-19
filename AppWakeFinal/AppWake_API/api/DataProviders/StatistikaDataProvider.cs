using api.DTOs;
using api.Models;
using NHibernate;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace api.DataProviders
{
    public class StatistikaDataProvider
    {
        public IEnumerable<Statistika> GetStatistike()
        {
            ISession s = DataLayer.GetSession();

            IEnumerable<Statistika> statistike = s.Query<Statistika>().Select(x => x); //

            s.Close();

            return statistike;
        }

        /*public IEnumerable<Statistika> GetStatistikeByGroupId(int idGrupe)
        {
            ISession s = DataLayer.GetSession();

            IEnumerable<Statistika> statistike = s.Query<Statistika>()
                .Where(st => st.PripadaGrupi.Id == idGrupe)
                .Select(x => x); //vracamo sve statistike jedne grupe

            s.Close();

            return statistike;
        }*/

        public Statistika GetStatistika(int id)
        {
            ISession s = DataLayer.GetSession();

            return s.Query<Statistika>()
                .Where(st => st.Id == id)
                .Select(x => x)
                .FirstOrDefault();
        }

        public StatistikaDTO GetStatistikaDTO(int id)
        {
            ISession s = DataLayer.GetSession();

            Statistika stat = s.Query<Statistika>()
                .Where(st => st.Id == id)
                .Select(x => x)
                .FirstOrDefault();

            if (stat == null)
                return new StatistikaDTO();

            return new StatistikaDTO(stat);
        }

        public Object GetStatistikaAll(int idGrupe)
        {
            try
            {

                ISession s = DataLayer.GetSession();

                //prosek budjenja
                double prosekProbudjenih = 0;
                int broj = 0;
                int brojUspesnihBudjenja = 0;

                IEnumerable<Statistika> stats = s.Query<Statistika>()
                                                 .Where(st => st.PripadaGrupi.Id == idGrupe)
                                                 .Select(x => x); //sve statistike jedne grupe

                foreach(var v in stats)
                {
                    prosekProbudjenih += v.BrojProbudjenih / (double)v.BrojClanova;
                    broj++;
                    brojUspesnihBudjenja += v.BrojProbudjenih; //ovo ce biti koliko ljudi se u grupi do sada uspesno probudilo
                }
                if (broj != 0)
                    prosekProbudjenih /= broj; //ovo je prosek probudjenih pri svakom budjenju

                //najcesce prvi
                int idPrvog = 0;
                long brPrvi = 0;

                ISQLQuery sQuery = s.CreateSQLQuery("select s.ID_PRVOG, count(*) as broj from appwake.STATISTIKA s" +
                    " where s.ID_GRUPE = " + idGrupe + " group by s.id_prvog order by broj desc limit 1;");

                IList<object[]> resultt = sQuery.List<object[]>();

                foreach (object[] obj in resultt)
                {
                    if (obj[0] != null)
                        idPrvog = (int)obj[0];
                    if (obj[1] != null)
                        brPrvi = (long)obj[1];
                }

                KorisnikDataProvider kdp = new KorisnikDataProvider();
                Korisnik prvi = kdp.GetKorisnik(idPrvog);

                String usernamePrvog;
                if (prvi == null)
                    usernamePrvog = "[deleted]";
                else
                    usernamePrvog = prvi.Username;

                //najvise clanova

                long maksClanova = 0;
                string maksDatum = null;

                IQuery query2 = s.CreateSQLQuery("SELECT max(s.BROJ_CLANOVA), s.DATUM FROM STATISTIKA s WHERE s.ID_GRUPE=" + idGrupe);

                IList<object[]> result2 = query2.List<object[]>();

                foreach (object[] obj in result2)
                {
                    if (obj[0] != null)
                        maksClanova = (int)obj[0];
                    if (obj[1] != null)
                        maksDatum = (string)obj[1];
                }

                //DateTime? trycast = DateTime.Parse(maksDatum);

                return new
                {
                    prosekProbudjeni = prosekProbudjenih,
                    ukupnoProbudjeni = brojUspesnihBudjenja,
                    najcescePrviId = idPrvog,
                    najcescePrviUsername = usernamePrvog,
                    brojPrvihPrvi = brPrvi,
                    najviseClanova = maksClanova,
                    najviseClanovaDatum = maksDatum
                };
                
            }
            catch (Exception ex)
            {
                return new { response = -1 };
            }

            //fali try-catch

        }

        public Object AddStatistika(StatistikaDTO stat)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                Statistika statistika = new Statistika(stat);
                GrupaDataProvider gdp = new GrupaDataProvider();
                KorisnikDataProvider kdp = new KorisnikDataProvider();
                Grupa g = gdp.GetGrupa(stat.IdGrupe);
                statistika.PripadaGrupi = g;
                g.Statistike.Add(statistika);

                statistika.BrojClanova = g.BrojClanova; //broj clanova grupe u tom trenutku

                Korisnik prvi = null;

                DateTime currentDate = DateTime.MaxValue; 
                foreach(var v in g.Clanovi)
                {
                    Korisnik k = v.KorisnikJeClan;
                    if (k.RealnoVremeBudjenja != null && currentDate.CompareTo(k.RealnoVremeBudjenja) > 0)
                    {
                        currentDate = (DateTime) k.RealnoVremeBudjenja;
                        prvi = k;
                    }
                }

                statistika.Prvi = prvi;

                /*KorisnikDataProvider kdp = new KorisnikDataProvider();
                statistika.Prvi = kdp.GetKorisnik(stat.IdPrvog); */ //pri kreiranju statistike je nepoznato koji je prvi!

                int idStatistike = (int) s.Save(statistika);

                s.Flush();
                s.Close();

                return new { response = idStatistike };
            }
            catch (Exception ex)
            {
                return new { response = -1 };
            }
        }

        public Object RemoveStatistika(int id)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                Statistika st = s.Load<Statistika>(id);

                s.Delete(st);

                s.Flush();
                s.Close();

                return new { response = 0 };
            }
            catch (Exception ex)
            {
                return new { response = -1 };
            }
        }

        public Object UpdateStatistika(int id, StatistikaDTO st)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                Statistika stat = s.Load<Statistika>(id);

                Grupa grupa = stat.PripadaGrupi; 

                stat.Datum = st.Datum;                  
                stat.BrojProbudjenih = st.BrojProbudjenih;
                stat.BrojClanova = grupa.BrojClanova;

                //proci kroz bazu i naci prvog
                //za grupu resetovati zeljeno vreme budjenja --ne ovde vec u posebnoj fji za grupu
                
                if (stat.Prvi == null && st.IdPrvog > 0) //dodat je prvi
                {
                    KorisnikDataProvider kdp = new KorisnikDataProvider();
                    stat.Prvi = kdp.GetKorisnik(st.IdPrvog);
                }

                s.Update(stat);

                s.Flush();
                s.Close();

                return new { response = 0 };
            }
            catch (Exception ex)
            {
                return new { response = -1 };
            }
        }
    }
}
using api.DTOs;
using api.Models;
using AutoMapper;
using NHibernate;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace api.DataProviders
{
    public class GrupaDataProvider
    {
        public IEnumerable<Grupa> GetGrupe()
        {
            ISession s = DataLayer.GetSession();

            IEnumerable<Grupa> grupe = s.Query<Grupa>().Select(x => x); //

            //s.Close();

            return grupe;
        }

        public IEnumerable<GrupaDTO> GetGrupeDTOSve()
        {
            ISession s = DataLayer.GetSession();

            IEnumerable<Grupa> grupe = s.Query<Grupa>().Select(x => x); //

            IList<GrupaDTO> grupeDTO = new List<GrupaDTO>();

            foreach (var v in grupe)
            {
                grupeDTO.Add(new GrupaDTO(v));
            }

            //s.Close();

            return grupeDTO;
        }

        public Grupa GetGrupa(int id)
        {
            ISession s = DataLayer.GetSession();

            return s.Query<Grupa>()
                .Where(g => g.Id == id)
                .Select(x => x)
                .FirstOrDefault();
        }

        public GrupaDTO GetGrupaDTO(int id)
        {
            ISession s = DataLayer.GetSession();

            Grupa grupa = s.Query<Grupa>()
                .Where(g => g.Id == id)
                .Select(x => x)
                .FirstOrDefault();

            if (grupa == null)
                return new GrupaDTO(); /////////

            return new GrupaDTO(grupa);
        }

        public Object AddGrupa(GrupaDTO grupa)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                Grupa gru = new Grupa(grupa);

                KorisnikDataProvider kdp = new KorisnikDataProvider(); /////////////novo!!!!
                Korisnik k = kdp.GetKorisnik(grupa.IdAdmina);
                gru.JeAdmin = k; //da li nam ovo treba uopste
                k.GrupeAdmin.Add(gru);

                int grupaId = (int) s.Save(gru);

                s.Flush();
                s.Close();

                return new { response = grupaId };
            }
            catch (Exception ex)
            {
                return new { response = -1 };
            }
        }

        public Object RemoveGrupa(int id)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                Grupa g = s.Load<Grupa>(id);

                foreach(var v in g.Clanovi)
                {
                    s.Delete(v); //brisemo sve clanove grupe
                }
                foreach(var v in g.Statistike)
                {
                    s.Delete(v); //brisemo sve statistike vezane za grupu
                }

                s.Delete(g);

                s.Flush();
                s.Close();

                return new { response = 0 };
            }
            catch (Exception ex)
            {
                return new { response = -1 };
            }
        }

        public Object UpdateGrupa(int id, GrupaDTO grupa) 
        {
            try
            {
                ISession s = DataLayer.GetSession();

                Grupa g = s.Load<Grupa>(id);

                if (grupa.BrojClanova > 0)
                    g.BrojClanova = grupa.BrojClanova;
                if (grupa.ZeljenoVremeBudjenja.Value.Year >= 2019)
                    g.ZeljenoVremeBudjenja = grupa.ZeljenoVremeBudjenja;
                if (grupa.IdAdmina > 0) ////
                    g.JeAdmin = s.Load<Korisnik>(grupa.IdAdmina); 

                s.Update(g);

                s.Flush();
                s.Close();

                return new { response = 0 };
            }
            catch (Exception ex)
            {
                return new { response = -1 };
            }
        }

        public Object UpdateGrupaResetZeljenoVremeBudjenja(int id)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                Grupa g = s.Load<Grupa>(id);

                g.ZeljenoVremeBudjenja = null;

                s.Update(g);

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
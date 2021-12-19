using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using NHibernate;
using api.Models;
using api.DTOs;
using AutoMapper;

namespace api.DataProviders
{
    public class KorisnikDataProvider
    {
        public IEnumerable<Korisnik> GetKorisnici() //neka se vracaju kao dtos
        {
            ISession s = DataLayer.GetSession();
            
            IEnumerable<Korisnik> korisnici = s.Query<Korisnik>().Select(x => x); //

            //s.Close();

            return korisnici;
        }

        public Korisnik GetKorisnik(int id)
        {
            ISession s = DataLayer.GetSession();

            return s.Query<Korisnik>()
                .Where(k => k.Id == id)
                .Select(x => x)
                .FirstOrDefault();
        }

        internal KorisnikDTO FindKorisnik(string email)
        {
            ISession s = DataLayer.GetSession();

            Korisnik korisnik = s.Query<Korisnik>().
                Where(k => k.Email == email).
                Select(x => x).FirstOrDefault();

            if (korisnik == null)
                return new KorisnikDTO();
            else
                return new KorisnikDTO(korisnik);
        }

        public KorisnikDTO GetKorisnikDTO(int id)
        {
            ISession s = DataLayer.GetSession();

            Korisnik korisnik = s.Query<Korisnik>()
                .Where(k => k.Id == id)
                .Select(x => x)
                .FirstOrDefault();

            if (korisnik == null)
                return new KorisnikDTO();

            return new KorisnikDTO(korisnik); 
        }

        public IEnumerable<KorisnikDTO> GetKorisnikDTOSvi()
        {
            ISession s = DataLayer.GetSession();

            IEnumerable<Korisnik> korisnici = s.Query<Korisnik>().Select(x => x); //

            IList<KorisnikDTO> korisniciDTO = new List<KorisnikDTO>();

            foreach (var v in korisnici)
            {
                korisniciDTO.Add(new KorisnikDTO(v));
            }
            //s.Close();

            return korisniciDTO;
        }

        public Object AddKorisnik(KorisnikDTO k)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                Korisnik korisnik = new Korisnik(k);

                int idKorisnika = (int) s.Save(korisnik);

                s.Flush();
                s.Close();

                return new { response = idKorisnika };
            }
            catch (Exception ex)
            {
                return new { response = -1 };
            }
        }

        public Object RemoveKorisnik(int id)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                Korisnik k = s.Load<Korisnik>(id);

                JeClanDataProvider jcdp = new JeClanDataProvider();

                foreach(var v in k.Grupe)
                {
                    v.DatumNapustanja = new DateTime(); //danasnji datum
                    s.Update(v);
                    if (v.JeClanGrupa.JeAdmin == k) //ako je on bio admin
                    {
                        IList<KorisnikDTO> clanovi = jcdp.GetClanoviGrupe(v.JeClanGrupa.Id).ToList();
                        if (clanovi.Count > 1)
                        {
                            if (clanovi[0].Id == k.Id) //on je prvi, a ima ih jos
                            {
                                v.JeClanGrupa.JeAdmin = s.Load<Korisnik>(clanovi[1].Id); //prvog sledeceg postavljamo za admina
                                s.Update(v);
                            }
                            else
                            {
                                v.JeClanGrupa.JeAdmin = s.Load<Korisnik>(clanovi[0].Id); //prvog postavljamo za admina
                                s.Update(v);
                            }

                        }

                    }
                }
                s.Delete(k);

                s.Flush();
                s.Close();

                return new { response = 0 };
            }
            catch (Exception ex)
            {
                return new { response = -1 };

            }
        }
        
        public Object UpdateKorisnik(int id, KorisnikDTO korisnik) 
        {
            try
            {
                ISession s = DataLayer.GetSession();

                Korisnik k = s.Load<Korisnik>(id);

                k.Ime = korisnik.Ime;
                k.Prezime = korisnik.Prezime;
                k.DatumRodjenja = korisnik.DatumRodjenja;
                if (korisnik.RealnoVremeBudjenja != null && korisnik.RealnoVremeBudjenja.Value.Year >= 2019) //ako je poslata validna vrednost
                    k.RealnoVremeBudjenja = korisnik.RealnoVremeBudjenja;
                k.Username = korisnik.Username;
                k.Slika = korisnik.Slika;
                k.Status = korisnik.Status;
                
                s.Update(k);

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
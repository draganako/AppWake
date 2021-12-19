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
    public class JeClanDataProvider
    {
        //useful:

        /// <summary>
        /// GET api/jeclan/id
        /// vraca dto
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        public JeClanDTO GetJeClanDTO(int id)
        {
            ISession s = DataLayer.GetSession();

            JeClan cl = s.Query<JeClan>()
                .Where(c => c.Id == id)
                .Select(x => x)
                .FirstOrDefault();

            if (cl == null)
                return new JeClanDTO();

            return new JeClanDTO(cl);
        }

        /// <summary>
        /// GET api/jeclan
        /// vraca sve jeclan kao dtos
        /// </summary>
        /// <returns></returns>
        public IEnumerable<JeClanDTO> GetJeClanDTOSvi()
        {
            try
            {
                ISession s = DataLayer.GetSession();

                IEnumerable<JeClan> jeClanSvi = s.Query<JeClan>().Select(x => x);

                IList<JeClanDTO> jeClanDTOs = new List<JeClanDTO>();

                foreach (var v in jeClanSvi)
                {
                    jeClanDTOs.Add(new JeClanDTO(v));
                }

                return jeClanDTOs;

            }
            catch (Exception ex)
            {
                return null;
            }
        }

        /// <summary>
        /// POST api/jeclan
        /// </summary>
        /// <returns></returns>
        public Object AddJeClan(JeClanDTO cl)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                Korisnik k = s.Load<Korisnik>(cl.IdKorisnika);
                Grupa g = s.Load<Grupa>(cl.IdGrupe);

                g.BrojClanova++; //dodat je novi clan
                s.Update(g); //azuriramo u bazi

                JeClan toSave = new JeClan(cl.DatumUclanjenja, cl.DatumNapustanja);
                toSave.KorisnikJeClan = k;
                toSave.JeClanGrupa = g;

                int jeClanId = (int) s.Save(toSave);

                s.Flush();
                s.Close();

                return new { response = jeClanId }; 
            }
            catch (Exception ex)
            {
                return new { response = -1 };
            }
        }

        /// <summary>
        /// GET api/jeclan/clanovi/idgrupe
        /// </summary>
        /// <param name="idGrupe"></param>
        /// <returns></returns>
        public IEnumerable<KorisnikDTO> GetClanoviGrupe(int idGrupe)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                IEnumerable<JeClan> clanoviGrupe = s.Query<JeClan>()
                                                    .Where(x => x.JeClanGrupa.Id == idGrupe)
                                                    .Where(x => x.DatumNapustanja == null) //samo oni koji su i dalje clanovi
                                                    .Select(x => x);

                IList<KorisnikDTO> korisniciClanovi = new List<KorisnikDTO>();

                foreach(var v in clanoviGrupe)
                {
                    korisniciClanovi.Add(new KorisnikDTO(v.KorisnikJeClan));
                }

                return korisniciClanovi;
            }
            catch(Exception e)
            {
                return null;
            }
        }

        public IEnumerable<KorisnikDTO> GetNisuClanoviGrupe(int idGrupe)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                IEnumerable<Korisnik> korisnici = s.Query<Korisnik>().Select(x => x); //svi korisnici

                IList<KorisnikDTO> korisniciDTO = new List<KorisnikDTO>();

                JeClanDataProvider jcdp = new JeClanDataProvider();

                foreach (var v in korisnici)
                {
                    Object obj = jcdp.FindJeClanIdKorisnikaIdGrupe(v.Id, idGrupe);
                    if (obj.Equals(new { response = -1 }))
                        korisniciDTO.Add(new KorisnikDTO(v)); //ako nije clan onda ga dodajemo u rezultujucu listu
                }

                return korisniciDTO;
            }
            catch (Exception e)
            {
                return null;
            }
        }

        /// <summary>
        /// GET api/jeclan/grupe/idkorisnika
        /// </summary>
        /// <returns></returns>
        public IEnumerable<GrupaDTO> GetGrupeClana(int idClana)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                IEnumerable<JeClan> clanoviGrupe = s.Query<JeClan>()
                                                    .Where(x => x.KorisnikJeClan.Id == idClana)
                                                    .Where(x => x.DatumNapustanja == null)
                                                    .Select(x => x);

                IList<GrupaDTO> grupeKorisnika = new List<GrupaDTO>();

                foreach (var v in clanoviGrupe)
                {
                    grupeKorisnika.Add(new GrupaDTO(v.JeClanGrupa));
                }

                return grupeKorisnika;
            }
            catch (Exception e)
            {
                return null;
            }
        }

        /// <summary>
        /// DELETE api/jeclan/id
        /// </summary>
        /// <returns></returns>
        public Object RemoveJeClan(int id)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                JeClan cl = s.Load<JeClan>(id);

                Grupa g = cl.JeClanGrupa;
                g.BrojClanova--;
                s.Update(g); //sklanjamo clana grupe

                s.Delete(cl);

                s.Flush();
                s.Close();

                return new { response = 0 };
            }
            catch (Exception ex)
            {
                return new { response = -1 };
            }
        }

        /// <summary>
        /// PUT api/jeclan/id
        /// </summary>
        /// <returns></returns>
        public Object UpdateJeClan(int id, JeClan jeClan)  ///////////////
        {
            try
            {
                ISession s = DataLayer.GetSession();

                JeClan jc = s.Load<JeClan>(id);

                if (jeClan.DatumNapustanja != null)
                {
                    jc.DatumNapustanja = jeClan.DatumNapustanja;
                    Grupa g = jc.JeClanGrupa;
                    g.BrojClanova--;
                    s.Update(g); //clan je napustio grupu -> smanjujemo broj clanova
                }

                s.Update(jc);

                s.Flush();
                s.Close();

                return new { response = 0 };
            }
            catch (Exception ex)
            {
                return new { response = -1 };
            }
        }

        /// <summary>
        /// GET api/jeclan/idkorisnika/idgrupe
        /// funkcija koja pronalazi id onog jeclan elementa koji ima datumnapustanja != null za odgovarajuceg clana i odgovarajucu grupu
        /// tako ce da se azurira kad je potrebno za njega, jer u suprotnom ne znamo koji je id koji treba da azuriramo kad clan
        /// napusta grupu
        /// ako ne postoji onaj sa datumnapustanja != null, onda ce da vrati false response
        /// </summary>
        /// <returns></returns>
        public Object FindJeClanIdKorisnikaIdGrupe(int idKorisnika, int idGrupe)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                JeClan clanGrupe = s.Query<JeClan>()
                                    .Where(x => x.KorisnikJeClan.Id == idKorisnika)
                                    .Where(x => x.JeClanGrupa.Id == idGrupe)
                                    .Where(x => x.DatumNapustanja == null)
                                    .Select(x => x)
                                    .FirstOrDefault();

                return new { response = clanGrupe.Id };
            }
            catch (Exception e)
            {
                return new { response = -1 };
            }
        }
        











        //ostalo:
        public IEnumerable<JeClan> GetJeClanSvi()
        {
            ISession s = DataLayer.GetSession();

            IEnumerable<JeClan> jeClanSvi = s.Query<JeClan>().Select(x => x); //

            //s.Close();

            return jeClanSvi;
        }

        public int RemoveJeClan(int idKorisnika, int idGrupe) ///////////try
        {
            try
            {
                ISession s = DataLayer.GetSession();

                JeClan cl = s.QueryOver<JeClan>()
                             .Where(x => x.JeClanGrupa.Id == idGrupe)
                             .Where(x => x.KorisnikJeClan.Id == idKorisnika)
                             .Select(x => x).SingleOrDefault();

                if (cl != null)
                    s.Delete(cl);

                s.Flush();
                s.Close();

                return 0;
            }
            catch (Exception ex)
            {
                return -1;
            }
        }

        

    }
}
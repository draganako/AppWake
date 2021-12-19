using api.DataProviders;
using api.DTOs;
using api.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace api.Controllers.API
{
    public class JeClanController : ApiController
    {
        /*////GET api/jeclan
        //public IEnumerable<JeClan> Get()
        //{
        //    JeClanDataProvider jcdp = new JeClanDataProvider();
        //    return jcdp.GetJeClanSvi();
        //}

        ////GET api/jeclan/3
        //public JeClan Get(int id)
        //{
        //    JeClanDataProvider jcdp = new JeClanDataProvider();
        //    return jcdp.GetJeClan(id);
        //}*/

        //GET api/jeclan
        public IEnumerable<JeClanDTO> Get()
        {
            JeClanDataProvider jcdp = new JeClanDataProvider();
            return jcdp.GetJeClanDTOSvi();
        }

        //GET api/jeclan/3
        public JeClanDTO Get(int id)
        {
            JeClanDataProvider jcdp = new JeClanDataProvider();
            return jcdp.GetJeClanDTO(id);
        }

        [Route("api/jeclan/clanovi/{grupaId}")]
        [HttpGet]
        public IEnumerable<KorisnikDTO> GetClanoviGrupe(int grupaId)
        {
            JeClanDataProvider jcdp = new JeClanDataProvider();
            return jcdp.GetClanoviGrupe(grupaId);
        }

        [Route("api/jeclan/nisuclanovi/{grupaId}")]
        [HttpGet]
        public IEnumerable<KorisnikDTO> GetNisuClanoviGrupe(int grupaId)
        {
            JeClanDataProvider jcdp = new JeClanDataProvider();
            return jcdp.GetNisuClanoviGrupe(grupaId);
        }

        [Route("api/jeclan/grupe/{korisnikId}")]
        [HttpGet]
        public IEnumerable<GrupaDTO> GetAllGroups(int korisnikId)
        {
            JeClanDataProvider jcdp = new JeClanDataProvider();
            return jcdp.GetGrupeClana(korisnikId);
        }


        //POST api/jeclan
        public Object Post([FromBody]JeClanDTO jc) ///zato sto prosledjujemo id korisnika i grupe kao int vrednosti
        {
            JeClanDataProvider jcdp = new JeClanDataProvider();
            return jcdp.AddJeClan(jc);
        }

        //DELETE api/jeclan/3
        public Object Delete(int id)
        {
            JeClanDataProvider jcdp = new JeClanDataProvider();
            return jcdp.RemoveJeClan(id);
        }

        //PUT api/jeclan/3
        public Object Put(int id, [FromBody]JeClan jc) 
            //moze samo datum napustanja da se azurira, ostalo nema smisla menjati? i realno vreme budjenja, ako ga negde koristimo!
        {
            JeClanDataProvider jcdp = new JeClanDataProvider();
            return jcdp.UpdateJeClan(id, jc);
        }

        [Route("api/jeclan/{idKorisnika}/{idGrupe}")]
        [HttpGet]
        public Object Find(int idKorisnika, int idGrupe)
        {
            JeClanDataProvider jcdp = new JeClanDataProvider();

            return jcdp.FindJeClanIdKorisnikaIdGrupe(idKorisnika, idGrupe);
        }
    }
}
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using api.DataProviders;
using api.DTOs;
using api.Models;

namespace proba2.Controllers.API
{
    public class KorisnikController : ApiController
    {
        //GET api/Korisnik
        public IEnumerable<KorisnikDTO> Get()
        {
            KorisnikDataProvider kdp = new KorisnikDataProvider();
            return kdp.GetKorisnikDTOSvi();
        }

        [Route("api/Korisnik/email/{email}/")]
        [HttpGet]
        public KorisnikDTO Get(String email)
        {
            KorisnikDataProvider kdp = new KorisnikDataProvider();

            return kdp.FindKorisnik(email);
        }
        
        //GET api/Korisnik/3
        public KorisnikDTO Get(int id)
        {
            KorisnikDataProvider kdp = new KorisnikDataProvider();
            return kdp.GetKorisnikDTO(id);
        }

        //POST api/Korisnik
        public Object Post([FromBody]KorisnikDTO k)
        {
            KorisnikDataProvider kdp = new KorisnikDataProvider();
            return kdp.AddKorisnik(k);
        }

        //DELETE api/Korisnik/3
        public Object Delete(int id)
        {
            KorisnikDataProvider kdp = new KorisnikDataProvider();
            return kdp.RemoveKorisnik(id);
        }

        //PUT api/Korisnik/3
        public Object Put(int id, [FromBody]KorisnikDTO k)
        {
            KorisnikDataProvider kdp = new KorisnikDataProvider();
            return kdp.UpdateKorisnik(id, k);
        }
    }
}
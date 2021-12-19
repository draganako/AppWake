using api.DataProviders;
using api.DTOs;
using api.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;

namespace api.Controllers.API
{
    public class GrupaController : ApiController
    {
        //GET api/grupa
        public IEnumerable<GrupaDTO> Get()
        {
            GrupaDataProvider gdp = new GrupaDataProvider();
            return gdp.GetGrupeDTOSve();
        }

        //GET api/grupa/3
        public GrupaDTO Get(int id)
        {
            GrupaDataProvider gdp = new GrupaDataProvider();
            return gdp.GetGrupaDTO(id);
        }

        //POST api/grupa
        public Object Post([FromBody]GrupaDTO g)
        {
            GrupaDataProvider gdp = new GrupaDataProvider();
            return gdp.AddGrupa(g);
        }

        //DELETE api/grupa/3
        public Object Delete(int id)
        {
            GrupaDataProvider gdp = new GrupaDataProvider();
            return gdp.RemoveGrupa(id);
        }

        //PUT api/grupa/3
        public Object Put(int id, [FromBody]GrupaDTO g)
        {
            GrupaDataProvider gdp = new GrupaDataProvider();
            return gdp.UpdateGrupa(id, g);
        }

        //PUT api/grupa/reset/id
        [Route("api/Grupa/reset/{id}/")]
        [HttpPut]
        public Object Reset(int id)
        {
            GrupaDataProvider gdp = new GrupaDataProvider();
            return gdp.UpdateGrupaResetZeljenoVremeBudjenja(id);
        }
    }
}
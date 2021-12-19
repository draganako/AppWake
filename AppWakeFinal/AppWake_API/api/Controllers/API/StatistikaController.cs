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
    public class StatistikaController : ApiController
    {
        //GET api/statistika
        public IEnumerable<Statistika> Get()
        {
            StatistikaDataProvider sdp = new StatistikaDataProvider();
            return sdp.GetStatistike();
        }

        //GET api/statistika/3
        public Object Get(int id)
        {
            StatistikaDataProvider sdp = new StatistikaDataProvider();
            return sdp.GetStatistikaAll(id);
        }

        //POST api/statistika
        public Object Post([FromBody]StatistikaDTO s)
        {
            StatistikaDataProvider sdp = new StatistikaDataProvider();
            return sdp.AddStatistika(s);
        }

        //DELETE api/statistika/3
        public Object Delete(int id)
        {
            StatistikaDataProvider sdp = new StatistikaDataProvider();
            return sdp.RemoveStatistika(id);
        }

        //PUT api/statistika/3
        public Object Put(int id, [FromBody]StatistikaDTO s)
        {
            StatistikaDataProvider sdp = new StatistikaDataProvider();
            return sdp.UpdateStatistika(id, s);
        }
    }
}
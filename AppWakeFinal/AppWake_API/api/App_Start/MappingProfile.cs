using api.DTOs;
using api.Models;
using AutoMapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace api.App_Start
{
    public class MappingProfile : Profile
    {
        public MappingProfile()
        {
            Mapper.CreateMap<JeClanDTO, JeClan>();
            Mapper.CreateMap<JeClan, JeClanDTO>();
            Mapper.CreateMap<Korisnik, KorisnikDTO>();
            Mapper.CreateMap<KorisnikDTO, Korisnik>();
        }
    }
}
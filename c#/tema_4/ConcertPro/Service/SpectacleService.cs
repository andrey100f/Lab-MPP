using System;
using System.Collections.Generic;
using ConcertPro.Dao.Model;
using ConcertPro.Dao.Repository.SpectacleRepository;
using log4net;

namespace ConcertPro.Service
{
    public class SpectacleService
    {
        private readonly ISpectacleRepository _spectacleRepository;
        private static readonly ILog Log = LogManager.GetLogger("SpectacleService");

        public SpectacleService(ISpectacleRepository spectacleRepository)
        {
            Log.InfoFormat("Initializing SpectacleService");
            
            _spectacleRepository = spectacleRepository;
            _spectacleRepository.FindAll();
        }

        public List<Spectacle> GetAllSpectacles()
        {
            Log.InfoFormat("Initializing the GetAllSpectacles task.");
            
            return (List<Spectacle>) _spectacleRepository.FindAll();
        }

        public List<Spectacle> GetAllSpectaclesByDate(DateTime date)
        {
            Log.InfoFormat("Initializing the GetSpectaclesByDate task");
            
            return (List<Spectacle>) _spectacleRepository.GetAllByDate(date);
        }
    }
}
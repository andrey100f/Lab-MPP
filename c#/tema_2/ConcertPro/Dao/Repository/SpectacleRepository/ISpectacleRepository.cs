using System;
using System.Collections.Generic;
using ConcertPro.Dao.Model;

namespace ConcertPro.Dao.Repository.SpectacleRepository
{
    public interface ISpectacleRepository : IRepository<Spectacle, long>
    {
        IEnumerable<Spectacle> GetAllByDate(DateTime date);
    }
}

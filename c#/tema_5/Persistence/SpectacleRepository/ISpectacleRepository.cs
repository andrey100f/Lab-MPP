using System;
using System.Collections.Generic;
using Model;

namespace Persistence.SpectacleRepository
{
    public interface ISpectacleRepository : IRepository<Spectacle, long>
    {
        IEnumerable<Spectacle> GetAllByDate(DateTime date);
    }
}

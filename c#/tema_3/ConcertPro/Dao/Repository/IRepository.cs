using System.Collections.Generic;
using ConcertPro.Dao.Model;

namespace ConcertPro.Dao.Repository
{
    public interface IRepository<T, TId> where T : IEntity<TId>
    {
        T FindOne(TId id);
        IEnumerable<T> FindAll();
        void Save(T entity);
        void Update(T entity, TId id);
        void Delete(TId id);
    }
}

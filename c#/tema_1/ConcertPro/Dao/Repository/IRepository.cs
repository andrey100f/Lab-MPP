using System.Collections.Generic;

namespace ConcertPro.Repository
{
    public interface IRepository<T>
    {
        T GetById(int id);
        IEnumerable<T> GetAll();
        T Add(T entity);
        T Update(T entity);
        void Delete(int id);
    }
}

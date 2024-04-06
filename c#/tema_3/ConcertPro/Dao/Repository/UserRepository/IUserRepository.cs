using ConcertPro.Dao.Model;

namespace ConcertPro.Dao.Repository.UserRepository
{
    public interface IUserRepository : IRepository<User, long>
    {
        User FindByUsernameAndPassword(string username, string password);
    }
}

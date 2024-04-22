using Model;

namespace Persistence.UserRepository
{
    public interface IUserRepository : IRepository<User, long>
    {
        User FindByUsernameAndPassword(string username, string password);
    }
}

using System;
using ConcertPro.Dao.Exception;
using ConcertPro.Dao.Model;
using ConcertPro.Dao.Repository.UserRepository;
using log4net;

namespace ConcertPro.Service
{
    public class UserService
    {
        private static readonly ILog Log = LogManager.GetLogger("UserService");
        private readonly IUserRepository _userRepository;

        public UserService(IUserRepository userRepository)
        {
            Log.InfoFormat("Initializing the UserService.");
            
            _userRepository = userRepository;
        }

        public void LoginUser(string username, string password)
        {
            Log.InfoFormat("Entering user login function.");

            User user = _userRepository.FindByUsernameAndPassword(username, password);

            if (user == null)
            {
                throw new ValidationException("Username sau parola gresite!!");
            }
            
            user.LoggedIn = true;
            _userRepository.Update(user, user.Id);
            
            Log.InfoFormat("User logged in. {0}", user);
        } 
    }
}

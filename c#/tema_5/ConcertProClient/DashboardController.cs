using System;
using Model;
using Services;

namespace ConcertProClient
{
    public class DashboardController : IConcertProObserver
    {
        // public event EventHandler<ConcertProEventArgs> UpdateEvent;
        private readonly IConcertProServices server;
        private User currentUsrer;

        public DashboardController(IConcertProServices server)
        {
            this.server = server;
            currentUsrer = null;
        }

        public void UserLoggedIn(User user)
        {
            throw new NotImplementedException();
        }

        public void UpdateSpectacles(Spectacle[] spectacles)
        {
            throw new NotImplementedException();
        }
    }
}
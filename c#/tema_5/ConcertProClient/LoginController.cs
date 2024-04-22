using System;
using System.Collections.Generic;
using Model;
using Services;

namespace ConcertProClient
{
    public class LoginController : IConcertProObserver
    {
        public event EventHandler<ConcertProEventArgs> updateEvent;
        private readonly IConcertProServices server;
        private User currentUsrer;
        
        public LoginController(IConcertProServices server)
        {
            this.server = server;
            currentUsrer = null;
        }
        
        public void login(String username, String password)
        {
            User user = new User(username, password, false);
            server.Login(user, this);
            Console.WriteLine("Login succeeded ....");
            currentUsrer = user;
            Console.WriteLine("Current user: {0}", currentUsrer);
        }

        public List<Spectacle> GetAllSpectacles()
        {
            Console.WriteLine("Getting all spectacles...");
            Spectacle[] spectacles = server.GetAllSpectacles();
            Console.WriteLine("Spectacles: {0}", spectacles);
            return new List<Spectacle>(spectacles);
        }

        public List<Spectacle> GetAllSpectaclesByDate(DateTime date)
        {
            Console.WriteLine("Geting the spectacles by date...");
            Spectacle[] spectacles = server.GetSpectaclesByDate(date);
            Console.WriteLine("Spectacles: {0}", spectacles);
            return new List<Spectacle>(spectacles);
        }

        public void AddOrder(Order order)
        {
            Console.WriteLine("Adding order...");
            server.AddOrder(order);
            Console.WriteLine("Order added.");
            
            Spectacle[] spectacles = server.GetAllSpectacles();
            
            ConcertProEventArgs eventArgs = new ConcertProEventArgs(EventType.UpdateSpectacles, spectacles);
            OnUserEvent(eventArgs);
        }
        
        public void UserLoggedIn(User user)
        {
            throw new System.NotImplementedException();
        }

        public void UpdateSpectacles(Spectacle[] spectacles)
        {
            Console.WriteLine("Updating spectacles...");
            ConcertProEventArgs eventArgs = new ConcertProEventArgs(EventType.UpdateSpectacles, spectacles);
            OnUserEvent(eventArgs);
        }

        protected virtual void OnUserEvent(ConcertProEventArgs e)
        {
            if (updateEvent == null)
            {
                return;
            }
            updateEvent(this, e);
            Console.WriteLine("Event fired.");
        }
    }
}
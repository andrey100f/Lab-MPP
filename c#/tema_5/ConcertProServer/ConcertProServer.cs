using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Model;
using Persistence.OrderRepository;
using Persistence.SpectacleRepository;
using Persistence.UserRepository;
using Services;

namespace ConcertProServer
{
    public class ConcertProServer : IConcertProServices
    {
        private IUserRepository _userRepository;
        private ISpectacleRepository _spectacleRepository;
        private IOrderRepository _orderRepository;
        private readonly IDictionary<String, IConcertProObserver> loggedClients;
        private User localUser;

        public ConcertProServer(IUserRepository userRepository, ISpectacleRepository spectacleRepository, IOrderRepository orderRepository)
        {
            _userRepository = userRepository;
            _spectacleRepository = spectacleRepository;
            _orderRepository = orderRepository;
            loggedClients = new Dictionary<string, IConcertProObserver>();
        }

        public void Login(User user, IConcertProObserver client)
        {
            User userR = _userRepository.FindByUsernameAndPassword(user.Username, user.Password);

            if (userR != null)
            {
                if (loggedClients.ContainsKey(userR.Username))
                {
                    throw new ConcertProException("User already logged in.");
                }
                loggedClients[userR.Username] = client;
                localUser = userR;
            }
            else
            {
                throw new ConcertProException("Authentication failed.");
            }
        }

        public Spectacle[] GetAllSpectacles()
        {
            IEnumerable<Spectacle> spectacles = _spectacleRepository.FindAll();
            Console.WriteLine("Server: " + spectacles);
            return spectacles.ToArray();
        }

        public Spectacle[] GetSpectaclesByDate(DateTime date)
        {
            IEnumerable<Spectacle> spectacles = _spectacleRepository.GetAllByDate(date);
            Console.WriteLine("Server: " + spectacles);
            return spectacles.ToArray();
        }

        public void AddOrder(Order order)
        {
            _orderRepository.Save(order);
            if(order.Spectacle.SeatsAvailable < order.NumberOfSeats)
                throw new ConcertProException("Not enough seats available.");

            Spectacle newSpectacle = order.Spectacle;
            
            newSpectacle.SeatsSold += order.NumberOfSeats;
            newSpectacle.SeatsAvailable -= order.NumberOfSeats;
            _spectacleRepository.Update(newSpectacle, newSpectacle.Id);
            
            NotifyClients();
        }

        private void NotifyClients()
        {
            var spectacles = _spectacleRepository.FindAll().ToArray();
            var users = _userRepository.FindAll();
            foreach (var us in users)
            {
                if(loggedClients.ContainsKey(us.Username) && us.Username != localUser.Username)
                {
                    Task.Run(() => loggedClients[us.Username].UpdateSpectacles(spectacles));
                }
            }
        }
    }
}
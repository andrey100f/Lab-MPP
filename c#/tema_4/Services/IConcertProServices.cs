using System;
using Model;

namespace Services
{
    public interface IConcertProServices
    {
        void Login(User user, IConcertProObserver observer);
        Spectacle[] GetAllSpectacles();
        Spectacle[] GetSpectaclesByDate(DateTime date);
        void AddOrder(Order order);
    }
}

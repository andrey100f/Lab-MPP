using ConcertPro.Dao.Exception;
using ConcertPro.Dao.Model;
using ConcertPro.Dao.Repository.OrderRepository;
using ConcertPro.Dao.Repository.SpectacleRepository;
using log4net;

namespace ConcertPro.Service
{
    public class OrderService
    {
        private readonly IOrderRepository _orderRepository;
        private readonly ISpectacleRepository _spectacleRepository;
        private static readonly ILog Log = LogManager.GetLogger("OrderService");

        public OrderService(IOrderRepository orderRepository, ISpectacleRepository spectacleRepository)
        {
            Log.InfoFormat("Initializing OrderService");
            
            _orderRepository = orderRepository;
            _spectacleRepository = spectacleRepository;
        }

        public void AddOrder(Order order)
        {
            Log.InfoFormat("Initializing the AddOrder task.");
            
            _orderRepository.Save(order);

            Spectacle spectacle = order.Spectacle;

            if (order.Spectacle.SeatsAvailable < order.NumberOfSeats)
            {
                throw new ValidationException("There are not enough seats for this spectacle!");
            }

            spectacle.SeatsSold += order.NumberOfSeats;
            spectacle.SeatsAvailable -= order.NumberOfSeats;
            _spectacleRepository.Update(spectacle, spectacle.Id);
        }
    }
}

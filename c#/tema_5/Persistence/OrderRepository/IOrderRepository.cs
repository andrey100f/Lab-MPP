using Model;

namespace Persistence.OrderRepository
{
    public interface IOrderRepository : IRepository<Order, long>
    {
    }
}

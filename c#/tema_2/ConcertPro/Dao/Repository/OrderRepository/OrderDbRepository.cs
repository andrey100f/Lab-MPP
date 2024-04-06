using System.Collections.Generic;
using System.Data;
using ConcertPro.Dao.Model;
using log4net;

namespace ConcertPro.Dao.Repository.OrderRepository
{
    public class OrderDbRepository : IOrderRepository
    {
        private static readonly ILog Log = LogManager.GetLogger("OrderDbRepository");
        private readonly IDictionary<string, string> _properties;

        public OrderDbRepository(IDictionary<string, string> properties)
        {
            Log.InfoFormat("Initializing OrderDbRepository with properties: {0}", properties);
            _properties = properties;
        }

        public Order FindOne(long id)
        {
            throw new System.NotImplementedException();
        }

        public IEnumerable<Order> FindAll()
        {
            throw new System.NotImplementedException();
        }

        public void Save(Order order)
        {
            Log.Info("Saving Order Task" + order);   
            IDbConnection connection = DbUtils.DbUtils.GetConnection(_properties);

            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "insert into orders (buyerName, spectacleId, numberOfSeats) values " +
                                      "(@buyerName, @spectacleId, @numberOfSeats)";
                
                IDbDataParameter buyerNameParameter = command.CreateParameter();
                buyerNameParameter.ParameterName = "@buyerName";
                buyerNameParameter.Value = order.BuyerName;
                command.Parameters.Add(buyerNameParameter);
                
                IDbDataParameter spectacleIdParameter = command.CreateParameter();
                spectacleIdParameter.ParameterName = "@spectacleId";
                spectacleIdParameter.Value = order.Spectacle.Id;
                command.Parameters.Add(spectacleIdParameter);
                
                IDbDataParameter numberOfSeatsParameter = command.CreateParameter();
                numberOfSeatsParameter.ParameterName = "@numberOfSeats";
                numberOfSeatsParameter.Value = order.NumberOfSeats;
                command.Parameters.Add(numberOfSeatsParameter);

                int result = command.ExecuteNonQuery();
                if (result == 0)
                {
                    Log.Error("Error DB: No Order Added");
                }
                else
                {
                    Log.Info("Saved " + result + " instances");
                }
            }
        }

        public void Update(Order entity, long id)
        {
            throw new System.NotImplementedException();
        }

        public void Delete(long id)
        {
            throw new System.NotImplementedException();
        }
    }
}
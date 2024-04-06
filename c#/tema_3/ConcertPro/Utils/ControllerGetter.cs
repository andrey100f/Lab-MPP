using System;
using System.Collections.Generic;
using System.Configuration;
using ConcertPro.Controller;
using ConcertPro.Dao.Repository.OrderRepository;
using ConcertPro.Dao.Repository.SpectacleRepository;
using ConcertPro.Dao.Repository.UserRepository;
using ConcertPro.Service;
using log4net;
using log4net.Config;

namespace ConcertPro.Utils
{
    public static class ControllerGetter
    {
        private static readonly ILog Log = LogManager.GetLogger("ControllerGetter");
        
        private static IDictionary<string, string> GetProperties()
        {
            XmlConfigurator.Configure(new System.IO.FileInfo("App.config"));
            Log.InfoFormat("Configuration Settings for database {0}",GetConnectionStringByName("database"));
            IDictionary<String, string> properties = new SortedList<String, String>();
            properties.Add("ConnectionString", GetConnectionStringByName("database"));

            return properties;
        }
        
        private static string GetConnectionStringByName(string name)
        {
            // Assume failure.
            string returnValue = null;

            // Look for the name in the connectionStrings section.
            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings[name];

            // If found, return the connection string.
            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }

        public static Login GetLogin()
        {
            Log.InfoFormat("Get the LoginController.");
            
            var properties = GetProperties();

            IUserRepository userRepository = new UserDbRepository(properties);
            UserService userService = new UserService(userRepository);

            return new Login(userService);
        }

        public static Dashboard GetDashboard()
        {
            Log.InfoFormat("Get the DashboardController.");
            
            var properties = GetProperties();

            ISpectacleRepository spectacleRepository = new SpectacleDbRepository(properties);
            SpectacleService spectacleService = new SpectacleService(spectacleRepository);

            IOrderRepository orderRepository = new OrderDbRepository(properties);
            OrderService orderService = new OrderService(orderRepository, spectacleRepository);

            return new Dashboard(spectacleService, orderService);
        }
    }
}

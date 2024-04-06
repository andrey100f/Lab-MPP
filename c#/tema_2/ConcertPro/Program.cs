using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using ConcertPro.Dao.Model;
using ConcertPro.Dao.Repository.OrderRepository;
using ConcertPro.Dao.Repository.SpectacleRepository;
using ConcertPro.Dao.Repository.UserRepository;
using log4net;
using log4net.Config;

namespace ConcertPro
{
    static class Program
    {
        
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            // Application.Run(new Form1());
            
            XmlConfigurator.Configure(new System.IO.FileInfo("App.config"));
            Console.WriteLine("Configuration Settings for database {0}",GetConnectionStringByName("database"));
            IDictionary<String, string> properties = new SortedList<String, String>();
            properties.Add("ConnectionString", GetConnectionStringByName("database"));

            // Test UserRepository
            IUserRepository userRepository = new UserDbRepository(properties);
            User user = userRepository.FindByUsernameAndPassword("john_doe", "parola123");
            Console.WriteLine("User-ul gasit este: " + user);
            
            User userToUpdate = new User("andrey", "andrey100f", true);
            userRepository.Update(userToUpdate, 2);
            
            // Test SpectacleRepository
            ISpectacleRepository spectacleRepository = new SpectacleDbRepository(properties);
            IList<Spectacle> spectacleList = (IList<Spectacle>) spectacleRepository.FindAll();
            Console.WriteLine("Toate spectacolele sunt: ");
            foreach(Spectacle s in spectacleList)
            {
                Console.WriteLine(s);      
            }

            Spectacle spectacle = spectacleRepository.FindOne(1);
            Console.WriteLine("Spectacolul cu id-ul 1 este: ");
            Console.WriteLine(spectacle);
            
            // Test OrderRepository
            IOrderRepository orderRepository = new OrderDbRepository(properties);
            Order order = new Order("andrei", spectacle, 2);
            orderRepository.Save(order);
        }
        
        static string GetConnectionStringByName(string name)
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
    }
}
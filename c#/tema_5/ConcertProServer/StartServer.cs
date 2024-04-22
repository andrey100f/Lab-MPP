using System;
using System.Collections.Generic;
using System.Configuration;
using System.Net.Sockets;
using System.Threading;
using Networking;
using Persistence.OrderRepository;
using Persistence.SpectacleRepository;
using Persistence.UserRepository;
using protobuf;
using Services;

namespace ConcertProServer
{
    public class StartServer
    {
        private static int DEFAULT_PORT=55556;
        private static string DEFAULT_IP="127.0.0.1";

        static void Main(string[] args)
        {
            Console.WriteLine("Reading properties from app.config...");
            int port = DEFAULT_PORT;
            string ip = DEFAULT_IP;
            string ports = ConfigurationManager.AppSettings["port"];
            if (ports == null)
            {
                Console.WriteLine("No port specified in app.config. Using default port: " + DEFAULT_PORT);
            }
            else
            {
                bool result = Int32.TryParse(ports, out port);
                if (!result)
                {
                    Console.WriteLine("Invalid port specified in app.config. Using default port: " + DEFAULT_PORT);
                    port = DEFAULT_PORT;
                    Console.WriteLine("Port: " + port);
                }
            }
            String ips = ConfigurationManager.AppSettings["ip"];

            if (ips == null)
            {
                Console.WriteLine("No ip specified in app.config. Using default ip: " + DEFAULT_IP);
            }
            Console.WriteLine("Configuration settings for database {0}", GetConnectionStringByName("database"));
            IDictionary<String, string> properties = new SortedList<String, String>();
            properties.Add("ConnectionString", GetConnectionStringByName("database"));
            
            IUserRepository userRepository = new UserDbRepository(properties);
            ISpectacleRepository spectacleRepository = new SpectacleDbRepository(properties);
            IOrderRepository orderRepository = new OrderDbRepository(properties);
            IConcertProServices service = new ConcertProServer(userRepository, spectacleRepository, orderRepository);
            Console.WriteLine("Starting server on IP {0} and port {1}", ip, port);

            ProtoServer server = new ProtoServer(ip, port, service);
            server.Start();
            Console.WriteLine("Server started...");
            Console.ReadLine();
        }
        
        static string GetConnectionStringByName(string name)
        {
            // Assume failure.
            string returnValue = null;

            // Look for the name in the connectionStrings section.
            ConnectionStringSettings settings =ConfigurationManager.ConnectionStrings[name];

            // If found, return the connection string.
            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }
    }

    public class SerialServer : ConcurrentServer
    {
        private IConcertProServices server;
        private ConcertProWorker worker;
        
        public SerialServer(string host, int port, IConcertProServices server) : base(host, port)
        {
            this.server = server;
            Console.WriteLine("SerialServer...");
        }
        
        protected override Thread CreateWorker(TcpClient client)
        {
            worker = new ConcertProWorker(server, client);
            return new Thread(worker.Run);
        }
    }
    
    public class ProtoServer : ConcurrentServer
    {
        private IConcertProServices server;
        private ProtoWorker worker;
        
        public ProtoServer(string host, int port, IConcertProServices server) : base(host, port)
        {
            this.server = server;
            Console.WriteLine("ProtoServer...");
        }
        
        protected override Thread CreateWorker(TcpClient client)
        {
            worker = new ProtoWorker(server, client);
            return new Thread(worker.run);
        }
    }
}
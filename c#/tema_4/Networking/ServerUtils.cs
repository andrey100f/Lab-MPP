using System;
using System.Net;
using System.Net.Sockets;
using System.Threading;

namespace Networking
{
    public abstract class AbstractServer
    {
        private TcpListener server;
        private String host;
        private int port;

        protected AbstractServer(string host, int port)
        {
            this.host = host;
            this.port = port;
        }

        public void Start()
        {
            IPAddress address = IPAddress.Parse(host);
            IPEndPoint endPoint = new IPEndPoint(address, port);
            server = new TcpListener(endPoint);
            server.Start();

            while (true)
            {
                Console.WriteLine("Waiting for clients...");
                TcpClient client = server.AcceptTcpClient();
                Console.WriteLine("Client connected...");
                ProcessRequest(client);
            }
        }
        
        public abstract void ProcessRequest(TcpClient client);
    }
    
    public abstract class ConcurrentServer : AbstractServer
    {
        protected ConcurrentServer(string host, int port) : base(host, port)
        {
        }

        public override void ProcessRequest(TcpClient client)
        {
            Thread t = CreateWorker(client);
            t.Start();
        }

        protected abstract Thread CreateWorker(TcpClient client);
    }
}
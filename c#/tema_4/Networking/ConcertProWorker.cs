using System;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using Model;
using Services;

namespace Networking
{
    public class ConcertProWorker : IConcertProObserver
    {
        private IConcertProServices server;
        private TcpClient connection;

        private NetworkStream _stream;
        private IFormatter _formatter;
        private volatile bool connected;

        public ConcertProWorker(IConcertProServices server, TcpClient connection)
        {
            this.server = server;
            this.connection = connection;

            try
            {
                _stream = connection.GetStream();
                _formatter = new BinaryFormatter();
                connected = true;
            }
            catch (Exception exception)
            {
                Console.WriteLine(exception.StackTrace);
            }
        }

        public virtual void Run()
        {
            while (connected)
            {
                try
                {
                    object request = _formatter.Deserialize(_stream);
                    object response = HandleRequest((Request)request);
                    if (response != null)
                    {
                        SendResponse((Response)response);
                    }
                }
                catch (Exception exception)
                {
                    Console.WriteLine(exception.StackTrace);
                    Console.WriteLine(exception.Message);
                }

                try
                {
                    Thread.Sleep(1000);
                }
                catch (Exception exception)
                {
                    Console.WriteLine(exception.StackTrace);
                }
            }
            
            try 
            {
                _stream.Close();
                connection.Close();
            } 
            catch (Exception exception) 
            {
                Console.WriteLine(exception.StackTrace);
            }
        }
        
        public void UserLoggedIn(User user)
        {
            Console.WriteLine("user logged in...");
            try
            {
                SendResponse(new OkResponse());
            }
            catch (Exception exception)
            {
                Console.WriteLine(exception.StackTrace);
            }
        }

        public void UpdateSpectacles(Spectacle[] spectacles)
        {
            Console.WriteLine("update spectacles...");
            try
            {
                SendResponse(new UpdateSpectaclesResponse(spectacles));
            }
            catch (Exception exception)
            {
                Console.WriteLine(exception.StackTrace);
            }
        }

        private Response HandleRequest(Request request)
        {
            Response response = null;
            if (request is LoginRequest)
            {
                Console.WriteLine("login request...");
                LoginRequest loginRequest = (LoginRequest)request;
                User user = loginRequest.User;
                try
                {
                    lock (server)
                    {
                        server.Login(user, this);
                    }

                    return new OkResponse();
                }
                catch (ConcertProException exception)
                {
                    connected = false;
                    return new ErrorResponse(exception.Message);
                }
            }
            
            if(request is GetSpectaclesRequest)
            {
                Console.WriteLine("get spectacles request...");
                try
                {
                    Spectacle[] spectacles;
                    lock (server)
                    {
                        spectacles = server.GetAllSpectacles();
                    }
                    return new GetSpectaclesResponse(spectacles);
                }
                catch (ConcertProException exception)
                {
                    return new ErrorResponse(exception.Message);
                }
            }

            if (request is GetFilteredSpectaclesRequest)
            {
                Console.WriteLine("get filtered spectacles request...");
                GetFilteredSpectaclesRequest req = (GetFilteredSpectaclesRequest) request;
                DateTime date = req.Date;
                try
                {
                    Spectacle[] spectacles;
                    lock (server)
                    {
                        spectacles = server.GetSpectaclesByDate(date);
                    }
                    return new GetFilteredSpectaclesResponse(spectacles);
                }
                catch (ConcertProException exception)
                {
                    return new ErrorResponse(exception.Message);
                }
            }

            if (request is AddOrderRequest)
            {
                Console.WriteLine("add order request...");
                AddOrderRequest req = (AddOrderRequest) request;
                Order order = req.Order;
                try
                {
                    lock (server)
                    {
                        server.AddOrder(order);
                    }
                    return new OkResponse();
                }
                catch(ConcertProException exception)
                {
                    return new ErrorResponse(exception.Message);
                }
            }

            return response;
        }
        
        private void SendResponse(Response response)
        {
            Console.WriteLine("sending response " + response);

            lock (_stream)
            {
                _formatter.Serialize(_stream, response);
                _stream.Flush();
            }
        }
    }
}

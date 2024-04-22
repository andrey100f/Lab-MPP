using System;
using System.Net.Sockets;
using System.Threading;
using Concertpro.Network.Protobuf;
using Google.Protobuf;
using Services;
using Order = Model.Order;
using Spectacle = Model.Spectacle;
using User = Model.User;

namespace protobuf
{
    public class ProtoWorker : IConcertProObserver
    {
        private IConcertProServices _server;
        private TcpClient _connection;
        private NetworkStream _stream;
        private volatile bool connected;

        public ProtoWorker(IConcertProServices server, TcpClient connection)
        {
            _server = server;
            _connection = connection;

            try
            {
                _stream = connection.GetStream();
                connected = true;
            }
            catch (Exception exception)
            {
                Console.WriteLine(exception.StackTrace);
            }
        }

        public virtual void run()
        {
            while (connected)
            {
                try
                {
                    Request request = Request.Parser.ParseDelimitedFrom(_stream);
                    Response response = HandleRequest(request);

                    if (response != null)
                    {
                        SendResponse(response);
                    }
                }
                catch (Exception exception)
                {
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
                _connection.Close();
            }
            catch (Exception exception)
            {
                Console.WriteLine("Error: " + exception);
            }
        }

        public void UserLoggedIn(User user)
        {
            throw new System.NotImplementedException();
        }

        public void UpdateSpectacles(Spectacle[] spectacles)
        {
            throw new System.NotImplementedException();
        }
        
        private Response HandleRequest(Request request)
        {
            Response response = null;
            Request.Types.Type requestType = request.Type;

            switch (requestType)
            {
                case Request.Types.Type.Login:
                {
                    Console.WriteLine("Login request ...");
                    User user = ProtoUtils.GetUser(request);

                    try
                    {
                        lock (_server)
                        {
                            _server.Login(user, this);
                        }
                        return ProtoUtils.CreateOkResponse();
                    }
                    catch (ConcertProException e)
                    {
                        return ProtoUtils.CreateErrorResponse(e.Message);
                    }
                }
                case Request.Types.Type.GetSpectacles:
                {
                    Console.WriteLine("Get spectacles request ...");

                    try
                    {
                        Spectacle[] spectacles;
                        lock (_server)
                        {
                            spectacles = _server.GetAllSpectacles();
                        }

                        return ProtoUtils.CreateGetSpectaclesResponse(spectacles);
                    }
                    catch (ConcertProException e)
                    {
                        return ProtoUtils.CreateErrorResponse(e.Message);
                    }
                }
                case Request.Types.Type.AddOrder:
                {
                    Console.WriteLine("Add order request ...");
                    try
                    {
                        Order order = ProtoUtils.GetOrder(request);
                        lock (_server)
                        {
                            _server.AddOrder(order);
                        }

                        return ProtoUtils.AddOrderResponse();
                    }
                    catch (ConcertProException e)
                    {
                        return ProtoUtils.CreateErrorResponse(e.Message);
                    }
                }
                case Request.Types.Type.GetFilteredSpectacles:
                {
                    Console.WriteLine("Get filtered spectacles request ...");
                    try
                    {
                        DateTime date = ProtoUtils.GetDate(request);
                        Spectacle[] spectacles;
                        lock (_server)
                        {
                            spectacles = _server.GetSpectaclesByDate(date);
                        }

                        return ProtoUtils.CreateGetFilteredSpectaclesResponse(spectacles);
                    }
                    catch (ConcertProException e)
                    {
                        return ProtoUtils.CreateErrorResponse(e.Message);
                    }
                }
                        
            }

            return response;
        }
        
        private void SendResponse(Response response)
        {
            Console.WriteLine("Sending response ..." + response);
            response.WriteDelimitedTo(_stream);
            _stream.Flush();
        }
    }
}
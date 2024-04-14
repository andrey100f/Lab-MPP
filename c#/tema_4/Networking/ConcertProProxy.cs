using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using Model;
using Services;

namespace Networking
{
    public class ConcertProProxy : IConcertProServices
    {
        private string host;
        private int port;

        private IConcertProObserver client;
        private NetworkStream _stream;
        private IFormatter _formatter;
        private TcpClient _connection;

        private Queue<Response> _responses;
        private volatile bool _finished;
        private EventWaitHandle _waitHandle;

        public ConcertProProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            _responses = new Queue<Response>();
        }

        public void Login(User user, IConcertProObserver client)
        {
            InitializeConnection();
            SendRequest(new LoginRequest(user));
            Response response = ReadResponse();
            if (response is OkResponse)
            {
                this.client = client;
                return;
            }

            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new ConcertProException(err.Message);
            }
        }

        public Spectacle[] GetAllSpectacles()
        {
            InitializeConnection();
            SendRequest(new GetSpectaclesRequest());
            Response response = ReadResponse();
            
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new ConcertProException(err.Message);
            }

            GetSpectaclesResponse res = (GetSpectaclesResponse)response;
            return res.Spectacles;
        }

        public Spectacle[] GetSpectaclesByDate(DateTime date)
        {
            InitializeConnection();
            SendRequest(new GetFilteredSpectaclesRequest(date));
            Response response = ReadResponse();
            
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new ConcertProException(err.Message);
            }
            
            GetFilteredSpectaclesResponse res = (GetFilteredSpectaclesResponse)response;
            return res.Spectacles;
        }

        public void AddOrder(Order order)
        {
            InitializeConnection();
            SendRequest(new AddOrderRequest(order));
            Response response = ReadResponse();

            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new ConcertProException(err.Message);
            }
        }

        private void CloseConnection()
        {
            _finished = true;
            try
            {
                _stream.Close();
                _connection.Close();
                _waitHandle.Close();
                client = null;
            }
            catch(Exception exception)
            {
                Console.WriteLine(exception.StackTrace);
            }
        }

        private void SendRequest(Request request)
        {
            try
            {
                _formatter.Serialize(_stream, request);
                _stream.Flush();
            }
            catch(Exception exception)
            {
                throw new ConcertProException("Error sending object " + exception);
            }
        }

        private Response ReadResponse()
        {
            Response response = null;
            try
            {
                _waitHandle.WaitOne();
                lock (_responses)
                {
                    response = _responses.Dequeue();
                }
            }
            catch(Exception exception)
            {
                Console.WriteLine(exception.StackTrace);
            }

            return response;
        }

        private void InitializeConnection()
        {
            try
            {
                _connection = new TcpClient(host, port);
                _stream = _connection.GetStream();
                _formatter = new BinaryFormatter();
                _finished = false;
                _waitHandle = new AutoResetEvent(false);
                StartReader();
            }
            catch(Exception exception)
            {
                Console.WriteLine(exception.StackTrace);
            }
        }

        private void HandleUpdate(UpdateResponse response)
        {
            if (response is UpdateSpectaclesResponse)
            {
                UpdateSpectaclesResponse res = (UpdateSpectaclesResponse)response;
                Console.WriteLine("HandleUpdate " + res);
                try
                {
                    client.UpdateSpectacles(res.Spectacles);
                }
                catch (ConcertProException exception)
                {
                    Console.WriteLine(exception.StackTrace);
                }
            }
        }

        private void StartReader()
        {
            Thread tw = new Thread(Run);
            tw.Start();
        }

        public virtual void Run()
        {
            while (!_finished)
            {
                try
                {
                    object response = _formatter.Deserialize(_stream);
                    Console.WriteLine("response received " + response);

                    if (response is UpdateResponse)
                    {
                        HandleUpdate((UpdateResponse) response);
                    }
                    else
                    {
                        lock (_responses)
                        {
                            _responses.Enqueue((Response) response);
                        }
                        _waitHandle.Set();
                    }
                }
                catch (Exception exception)
                {
                    Console.WriteLine(exception.Message);
                }
            }
        }
    }
}
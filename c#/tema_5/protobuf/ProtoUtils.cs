using System;
using Model;
using proto=Concertpro.Network.Protobuf;

namespace protobuf
{
    public class ProtoUtils
    {
        public static proto.Request CreateLoginRequest(Model.User user)
        {
            proto.User userDto = new proto.User {Id = user.Id, Username = user.Username, Password = user.Password, 
                LoggedIn = user.LoggedIn};
            proto.Request request = new proto.Request {Type = proto.Request.Types.Type.Login, User = userDto};

            return request;
        }

        public static proto.Response CreateOkResponse()
        {
            proto.Response response = new proto.Response {Type = proto.Response.Types.Type.Ok};
            return response;
        }
        
        public static proto.Response CreateErrorResponse(string text)
        {
            proto.Response response = new proto.Response {Type = proto.Response.Types.Type.Error, Error = text};
            return response;
        }

        public static proto.Request CreateAddOrderRequest(Order order)
        {
            proto.Order orderDto = new proto.Order
                { BuyerName = order.BuyerName, NumberOfSeats = order.NumberOfSeats };
            orderDto.Spectacle = new proto.Spectacle {Id = order.Spectacle.Id, 
                SpectaclePlace = order.Spectacle.SpectaclePlace, SpectacleDate = order.Spectacle.SpectacleDate.ToShortDateString(), 
                SeatsAvailable = order.Spectacle.SeatsAvailable, SeatsSold = order.Spectacle.SeatsSold, 
                ArtistName = order.Spectacle.ArtistName};
            proto.Request request = new proto.Request{Type = proto.Request.Types.Type.AddOrder, Order = orderDto};

            return request;
        }
        
        public static proto.Response CreateGetSpectaclesResponse(Model.Spectacle[] spectacles)
        {
            proto.Response response = new proto.Response {Type = proto.Response.Types.Type.GetSpectacles};
            foreach (Model.Spectacle spectacle in spectacles)
            {
                proto.Spectacle spectacleDto = new proto.Spectacle {Id = spectacle.Id, ArtistName = spectacle.ArtistName, 
                    SpectacleDate = spectacle.SpectacleDate.ToShortDateString(), SpectaclePlace = spectacle.SpectaclePlace, 
                    SeatsAvailable = spectacle.SeatsAvailable, SeatsSold = spectacle.SeatsSold};
                response.Spectacles.Add(spectacleDto);
            }

            return response;
        }

        public static proto.Response CreateGetFilteredSpectaclesResponse(Model.Spectacle[] spectacles)
        {
            proto.Response response = new proto.Response {Type = proto.Response.Types.Type.GetFilteredSpectacles};
            foreach (Model.Spectacle spectacle in spectacles)
            {
                proto.Spectacle spectacleDto = new proto.Spectacle {Id = spectacle.Id, SpectaclePlace = spectacle.SpectaclePlace,
                    ArtistName = spectacle.ArtistName, SpectacleDate = spectacle.SpectacleDate.ToShortDateString(), 
                    SeatsAvailable = spectacle.SeatsAvailable, SeatsSold = spectacle.SeatsSold};
                response.Spectacles.Add(spectacleDto);
            }

            return response;
        }

        public static string GetError(proto.Response response)
        {
            String errorMessage = response.Error;
            return errorMessage;
        }

        public static Model.User GetUser(proto.Response response)
        {
            Model.User user = new Model.User {Id = response.User.Id, Username = response.User.Username, 
                Password = response.User.Password, LoggedIn = response.User.LoggedIn};
            return user;
        }

        public static Model.User GetUser(proto.Request request)
        {
            Model.User user = new Model.User {Id = request.User.Id, Username = request.User.Username, 
                Password = request.User.Password, LoggedIn = request.User.LoggedIn};
            return user;
        }

        public static proto.Response AddOrderResponse()
        {
            proto.Response response = new proto.Response{ Type = proto.Response.Types.Type.AddedOrder };
            return response;
        }

        public static Model.Order GetOrder(proto.Request request)
        {
            Spectacle spectacle = new Spectacle
            {
                Id = request.Order.Spectacle.Id,
                ArtistName = request.Order.Spectacle.ArtistName,
                SpectacleDate = DateTime.Parse(request.Order.Spectacle.SpectacleDate),
                SpectaclePlace = request.Order.Spectacle.SpectaclePlace,
                SeatsAvailable = request.Order.Spectacle.SeatsAvailable,
                SeatsSold = request.Order.Spectacle.SeatsSold
            };
            Model.Order order = new Model.Order {BuyerName = request.Order.BuyerName, 
                Spectacle = spectacle, NumberOfSeats = request.Order.NumberOfSeats};
            return order;
        }

        public static DateTime GetDate(proto.Request request)
        {
            DateTime date = DateTime.Parse(request.Date);
            return date;
        }
    }
}

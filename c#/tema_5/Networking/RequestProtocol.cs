using System;
using Model;

namespace Networking
{
    public interface Request
    {
        
    }

    [Serializable]
    public class LoginRequest : Request
    {
        private User _user;
        
        public LoginRequest(User user)
        {
            _user = user;
        }

        public virtual User User => _user;
    }
    
    [Serializable]
    public class AddOrderRequest : Request
    {
        private Order _order;
        
        public AddOrderRequest(Order order)
        {
            _order = order;
        }

        public virtual Order Order => _order;
    }

    [Serializable]
    public class GetFilteredSpectaclesRequest : Request
    {
        private DateTime _date;
        
        public GetFilteredSpectaclesRequest(DateTime date)
        {
            _date = date;
        }
        
        public virtual DateTime Date => _date;
    }
    
    [Serializable]
    public class GetSpectaclesRequest : Request
    {
    }

    [Serializable]
    public class UpdateSpectaclesRequest : Request
    {
        private Spectacle[] _spectacles;
        
        public UpdateSpectaclesRequest(Spectacle[] spectacles)
        {
            _spectacles = spectacles;
        }
        
        public virtual Spectacle[] Spectacles => _spectacles;
    }
}
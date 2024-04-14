using System;
using Model;

namespace Networking
{
    public interface Response
    {
    }

    [Serializable]
    public class ErrorResponse : Response
    {
        private string _message;
        
        public ErrorResponse(string message)
        {
            _message = message;
        }
        
        public virtual string Message => _message;
    }
    
    [Serializable]
    public class OkResponse : Response
    {
    }

    [Serializable]
    public class GetSpectaclesResponse : Response
    {
        private Spectacle[] _spectacles;
        
        public GetSpectaclesResponse(Spectacle[] spectacles)
        {
            _spectacles = spectacles;
        }
        
        public virtual Spectacle[] Spectacles => _spectacles;
    }
    
    [Serializable]
    public class GetFilteredSpectaclesResponse : Response
    {
        private Spectacle[] _spectacles;
        
        public GetFilteredSpectaclesResponse(Spectacle[] spectacles)
        {
            _spectacles = spectacles;
        }
        
        public virtual Spectacle[] Spectacles => _spectacles;
    }

    public interface UpdateResponse : Response
    {
    }
    
    [Serializable]
    public class UpdateSpectaclesResponse : UpdateResponse
    {
        private Spectacle[] _spectacles;
        
        public UpdateSpectaclesResponse(Spectacle[] spectacles)
        {
            _spectacles = spectacles;
        }
        
        public virtual Spectacle[] Spectacles => _spectacles;
    }
}

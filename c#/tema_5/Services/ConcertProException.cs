using System;

namespace Services
{
    public class ConcertProException : Exception
    {
        public ConcertProException()
        {
        }

        public ConcertProException(string message) : base(message)
        {
        }

        public ConcertProException(string message, Exception innerException) : base(message, innerException)
        {
        }
    }
}

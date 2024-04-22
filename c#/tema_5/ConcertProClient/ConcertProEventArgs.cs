using System;

namespace ConcertProClient
{
    public enum EventType
    {
        UpdateSpectacles
    };
    
    public class ConcertProEventArgs : EventArgs
    {
        private readonly EventType _eventType;
        private readonly Object _data;

        public ConcertProEventArgs(EventType eventType, object data)
        {
            _eventType = eventType;
            _data = data;
        }
        
        public EventType EventType => _eventType;
        
        public Object Data => _data;
    }
}
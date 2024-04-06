using System;

namespace ConcertPro.Model
{
    public class Spectacle
    {
        private long SpectacleId { get; set; }
        private string ArtistName { get; set; }
        private DateTime SpectacleDate { get; set; }
        private string SpectaclePlace { get; set; }
        private long SeatsAvailable { get; set; }
        private long SeatsSold { get; set; }

        public Spectacle() { }

        public Spectacle(long spectacleId, string artistName, DateTime spectacleDate, string spectaclePlace, 
            long seatsAvailable, long seatsSold)
        {
            SpectacleId = spectacleId;
            ArtistName = artistName;
            SpectacleDate = spectacleDate;
            SpectaclePlace = spectaclePlace;
            SeatsAvailable = seatsAvailable;
            SeatsSold = seatsSold;
        }
    }
}

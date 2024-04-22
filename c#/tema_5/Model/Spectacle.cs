using System;

namespace Model
{
    [Serializable]
    public class Spectacle : IEntity<long>
    {
        public long Id { get; set; }
        public string ArtistName { get; set; }
        public DateTime SpectacleDate { get; set; }
        public string SpectaclePlace { get; set; }
        public long SeatsAvailable { get; set; }
        public long SeatsSold { get; set; }

        public Spectacle()
        {
            Id = 0L;
            ArtistName = "";
            SpectacleDate = new DateTime();
            SpectaclePlace = "";
            SeatsAvailable = 0L;
            SeatsSold = 0L;
        }

        public Spectacle(string artistName, DateTime spectacleDate, string spectaclePlace, 
            long seatsAvailable, long seatsSold)
        {
            ArtistName = artistName;
            SpectacleDate = spectacleDate;
            SpectaclePlace = spectaclePlace;
            SeatsAvailable = seatsAvailable;
            SeatsSold = seatsSold;
        }
    }
}

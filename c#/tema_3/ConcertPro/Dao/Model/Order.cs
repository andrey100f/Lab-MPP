namespace ConcertPro.Dao.Model
{
    public class Order : IEntity<long>
    {
        public long Id { get; set; }
        public string BuyerName { get; set; }
        public Spectacle Spectacle { get; set; }
        public long NumberOfSeats { get; set; }

        public Order()
        {
            Id = 0L;
            BuyerName = "";
            Spectacle = new Spectacle();
            NumberOfSeats = 0L;
        }

        public Order(string buyerName, Spectacle spectacle, int numberOfSeats)
        {
            BuyerName = buyerName;
            Spectacle = spectacle;
            NumberOfSeats = numberOfSeats;
        }
    }
}

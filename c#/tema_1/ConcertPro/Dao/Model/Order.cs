namespace ConcertPro.Model
{
    public class Order
    {
        private long OrderId { get; set; }
        private string BuyerName { get; set; }
        private Spectacle Spectacle { get; set; }
        private int NumberOfSeats { get; set; }

        public Order() { }

        public Order(long orderId, string buyerName, Spectacle spectacle, int numberOfSeats)
        {
            OrderId = orderId;
            BuyerName = buyerName;
            Spectacle = spectacle;
            NumberOfSeats = numberOfSeats;
        }
    }
}

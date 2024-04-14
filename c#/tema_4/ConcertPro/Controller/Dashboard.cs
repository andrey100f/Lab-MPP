using System;
using System.Windows.Forms;
using ConcertPro.Dao.Exception;
using ConcertPro.Dao.Model;
using ConcertPro.Service;
using ConcertPro.Utils;
using log4net;

namespace ConcertPro.Controller
{
    public partial class Dashboard : Form
    {
        private static readonly ILog Log = LogManager.GetLogger("DashboardController");
        private readonly SpectacleService _spectacleService;
        private readonly OrderService _orderService;
        private Spectacle _spectacle;
        
        public Dashboard(SpectacleService spectacleService, OrderService orderService)
        {
            Log.InfoFormat("Initializing DashboardController.");
            
            _spectacleService = spectacleService;
            _orderService = orderService;
            
            InitializeComponent();
        }

        private void LogoutButton_Click(object sender, EventArgs e)
        {
            Log.InfoFormat("Initializing Logout task");
            
            Login login = ControllerGetter.GetLogin();
            Hide();
            login.Show();
        }

        private void BuyButton_Click(object sender, EventArgs e)
        {
            Log.InfoFormat("Initializing Buy Task");
            
            try
            {
                var order = GetOrder();
                _orderService.AddOrder(order);
                var spectacles = _spectacleService.GetAllSpectacles();
                SpectaclesDataGridView.DataSource = spectacles;
                MessageBox.Show("Comanda adaugata cu success!");
                ResetInputs();
            }
            catch (ValidationException exception)
            {
                Log.Error(exception.Message);
                MessageBox.Show(exception.Message);
            }
        }

        private void ResetInputs()
        {
            BuyerNameInput.Text = null;
            NumberOfTicketsInput.Text = null;
            _spectacle = null;
            FilterSpectaclesDataGridView.DataSource = null;
        }

        private void Dashboard_Load(object sender, EventArgs e)
        {
            Log.InfoFormat("Populating the Spectacles table");
            
            var spectacles = _spectacleService.GetAllSpectacles();
            SpectaclesDataGridView.DataSource = spectacles;
        }

        private void FilterButton_Click(object sender, EventArgs e)
        {
            Log.InfoFormat("Initializing the filter task");
            
            var spectacleDate = SpectacleDateInput.Value.Date;
            var spectacles = _spectacleService.GetAllSpectaclesByDate(spectacleDate);
            
            FilterSpectaclesDataGridView.DataSource = spectacles;
        }

        private void ResetFilterButton_Click(object sender, EventArgs e)
        {
            Log.InfoFormat("Initializing the reset filter task");
            
            FilterSpectaclesDataGridView.DataSource = null;
        }

        private void FilterSpectaclesDataGridView_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            Log.InfoFormat("Initializing the select spectacle task");
            
            var spectacleSelected = FilterSpectaclesDataGridView.Rows[e.RowIndex];
            var spectacleId = (long) spectacleSelected.Cells[0].Value;
            var spectacleName = spectacleSelected.Cells[1].Value.ToString();
            var spectacleDate = (DateTime) spectacleSelected.Cells[2].Value;
            var spectaclePlace = spectacleSelected.Cells[3].Value.ToString();
            var seatsAvailable = (long) spectacleSelected.Cells[4].Value;
            var seatsSold = (long) spectacleSelected.Cells[5].Value;

            var spectacle = new Spectacle(spectacleName, spectacleDate, spectaclePlace, seatsAvailable,
                seatsSold);
            spectacle.Id = spectacleId;

            _spectacle = spectacle;
            
            Log.InfoFormat("Spectacle selected " + spectacle);

            MessageBox.Show("Spectacol selectat!");
        }

        private Order GetOrder()
        {
            if (this.FilterSpectaclesDataGridView.Rows.Count == 0)
            {
                throw new ValidationException("Nu exista spectacol la data specificata!!");
            }

            if (NumberOfTicketsInput.Text.Equals("") || BuyerNameInput.Text.Equals(""))
            {
                throw new ValidationException("Inputs should not be null!!");
            }
            
            var buyerName = this.BuyerNameInput.Text;
            var numberOfTickets = int.Parse(this.NumberOfTicketsInput.Text);
            var spectacle = _spectacle;

            return new Order(buyerName, spectacle, numberOfTickets);
        }
    }
}

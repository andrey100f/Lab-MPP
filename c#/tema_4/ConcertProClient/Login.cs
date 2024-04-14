using System;
using System.Windows.Forms;

namespace ConcertProClient
{
    public partial class Login : Form
    {
        private readonly LoginController _loginController;
        // private static readonly ILog Log = LogManager.GetLogger("LoginController");

        public Login(LoginController loginController)
        {
            // Log.InfoFormat("Initializing the LoginController.");

            _loginController = loginController;
            InitializeComponent();
        }

        private void LoginButton_Click(object sender, EventArgs e)
        {
            // Log.InfoFormat("Initializing the login task");
            
            var username = UsernameInput.Text;
            var password = PasswordInput.Text;
            
            try
            {
                _loginController.login(username, password);
                
                var dashboard = new Dashboard(_loginController);
                dashboard.Show();
                Hide();

                // var dashboard = ControllerGetter.GetDashboard();
                // Hide();
                // dashboard.Show();
            }
            catch (Exception exception)
            {
                // Log.Error(exception.Message);
                MessageBox.Show(exception.Message);
            }
        }

        private void ExitButton_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }
    }
}

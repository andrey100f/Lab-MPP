using System;
using System.Windows.Forms;
using ConcertPro.Dao.Exception;
using ConcertPro.Service;
using ConcertPro.Utils;
using log4net;

namespace ConcertPro.Controller
{
    public partial class Login : Form
    {
        private readonly UserService _userService;
        private static readonly ILog Log = LogManager.GetLogger("LoginController");

        public Login(UserService userService)
        {
            Log.InfoFormat("Initializing the LoginController.");
            
            _userService = userService;
            
            InitializeComponent();
        }

        private void LoginButton_Click(object sender, EventArgs e)
        {
            Log.InfoFormat("Initializing the login task");
            
            var username = UsernameInput.Text;
            var password = PasswordInput.Text;
            
            try
            {
                _userService.LoginUser(username, password);

                var dashboard = ControllerGetter.GetDashboard();
                Hide();
                dashboard.Show();
            }
            catch (ValidationException exception)
            {
                Log.Error(exception.Message);
                MessageBox.Show(exception.Message);
            }
        }

        private void ExitButton_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }
    }
}

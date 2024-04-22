using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using Networking;
using Services;

namespace ConcertProClient
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            IConcertProServices server = new ConcertProProxy("127.0.0.1", 55556);
            LoginController loginController = new LoginController(server);
            Login login = new Login(loginController);
            Application.Run(login);
        }
    }
}
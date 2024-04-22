using System;
using System.Windows.Forms;
using ConcertPro.Utils;
namespace ConcertPro
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
            Application.Run(ControllerGetter.GetLogin());
        }
    }
}

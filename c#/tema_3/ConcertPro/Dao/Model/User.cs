namespace ConcertPro.Dao.Model
{
    public class User : IEntity<long>
    {
        public long Id { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }
        public bool LoggedIn { get; set; }


        public User()
        {
            Id = 0L;
            Username = "";
            Password = "";
            LoggedIn = false;
        }

        public User(string username, string password, bool loggedIn)
        {
            Username = username;
            Password = password;
            LoggedIn = loggedIn;
        }
    }
}

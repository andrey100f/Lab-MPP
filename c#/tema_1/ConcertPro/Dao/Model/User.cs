namespace ConcertPro.Model
{
    public class User
    {
        private int UserId { get; set; }
        private string Username { get; set; }
        private string Password { get; set; }

        public User() { }

        public User(int userId, string username, string password)
        {
            UserId = userId;
            Username = username;
            Password = password;
        }
    }
}

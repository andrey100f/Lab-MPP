using Model;

namespace Services
{
    public interface IConcertProObserver
    {
        void UserLoggedIn(User user);
        void UpdateSpectacles(Spectacle[] spectacles);
    }
}
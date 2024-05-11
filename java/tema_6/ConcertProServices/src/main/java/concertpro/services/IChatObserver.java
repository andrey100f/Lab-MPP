package concertpro.services;

import concertpro.model.Spectacle;
import concertpro.model.User;

public interface IChatObserver {

    void userLoggedIn(User friend) throws ChatException;
    void userLoggedOut(User friend) throws ChatException;

    void updateSpectacles(Spectacle[] spectacles) throws ChatException;

}

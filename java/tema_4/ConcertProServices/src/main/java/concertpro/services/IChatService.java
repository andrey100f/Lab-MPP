package concertpro.services;

import concertpro.model.Order;
import concertpro.model.Spectacle;
import concertpro.model.User;

import java.time.LocalDate;

public interface IChatService {

    void login(User user, IChatObserver client) throws ChatException;
    void logout(User user, IChatObserver client) throws ChatException;
    User[] getLoggedUsers(User user) throws ChatException;

    Spectacle[] getAllSpectacles() throws ChatException;
    Spectacle[] getSpectaclesByDate(LocalDate date) throws ChatException;
    void addOrder(Order order) throws ChatException;

}

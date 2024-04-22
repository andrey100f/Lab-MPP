package ro.mpp2024.concertpro.utils.observer;

import ro.mpp2024.concertpro.utils.event.Event;

public interface Observable<E extends Event> {
    void addObserver(Observer<E> observer);
    void deleteObserver(Observer<E> observer);
    void notifyObservers(E event);
}

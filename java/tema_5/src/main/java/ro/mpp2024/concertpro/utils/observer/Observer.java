package ro.mpp2024.concertpro.utils.observer;

import ro.mpp2024.concertpro.utils.event.Event;

public interface Observer<E extends Event> {
    void update(E event);
}

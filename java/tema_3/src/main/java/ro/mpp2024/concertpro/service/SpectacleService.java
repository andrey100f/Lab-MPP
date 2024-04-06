package ro.mpp2024.concertpro.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2024.concertpro.dao.model.Spectacle;
import ro.mpp2024.concertpro.dao.repository.spectacle_repository.ISpectacleRepository;
import ro.mpp2024.concertpro.utils.event.SpectacleChangeEventType;
import ro.mpp2024.concertpro.utils.observer.Observable;
import ro.mpp2024.concertpro.utils.observer.Observer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SpectacleService implements Observable<SpectacleChangeEventType> {
    private final List<Observer<SpectacleChangeEventType>> listOfObservers = new ArrayList<>();
    private final ISpectacleRepository spectacleRepository;
    private static final Logger logger = LogManager.getLogger();

    public SpectacleService(ISpectacleRepository spectacleRepository) {
        logger.info("Initializing Spectacle Service");
        this.spectacleRepository = spectacleRepository;
    }

    public List<Spectacle> getAllSpectacles() {
        return (List<Spectacle>) this.spectacleRepository.findAll();
    }

    public List<Spectacle> getAllSpectaclesByDate(LocalDate date) {
        return (List<Spectacle>) this.spectacleRepository.getAllByDate(date);
    }

    @Override
    public void addObserver(Observer<SpectacleChangeEventType> observer) {
        this.listOfObservers.add(observer);
    }

    @Override
    public void deleteObserver(Observer<SpectacleChangeEventType> observer) {
        this.listOfObservers.remove(observer);
    }

    @Override
    public void notifyObservers(SpectacleChangeEventType event) {
        this.listOfObservers.forEach(observer -> observer.update(event));
    }
}

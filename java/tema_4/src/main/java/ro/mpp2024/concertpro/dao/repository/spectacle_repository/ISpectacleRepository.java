package ro.mpp2024.concertpro.dao.repository.spectacle_repository;

import ro.mpp2024.concertpro.dao.model.Spectacle;
import ro.mpp2024.concertpro.dao.repository.IRepository;

import java.time.LocalDate;
import java.util.Date;

public interface ISpectacleRepository extends IRepository<Spectacle, Long> {
    Iterable<Spectacle> getAllByDate(LocalDate date);
}

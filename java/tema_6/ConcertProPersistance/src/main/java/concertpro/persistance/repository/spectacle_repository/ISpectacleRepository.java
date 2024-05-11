package concertpro.persistance.repository.spectacle_repository;

import concertpro.model.Spectacle;
import concertpro.persistance.repository.IRepository;

import java.time.LocalDate;

public interface ISpectacleRepository extends IRepository<Spectacle, Long> {

    Iterable<Spectacle> getAllByDate(LocalDate date);

}

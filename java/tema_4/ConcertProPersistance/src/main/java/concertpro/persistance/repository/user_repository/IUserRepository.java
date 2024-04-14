package concertpro.persistance.repository.user_repository;


import concertpro.model.User;
import concertpro.persistance.repository.IRepository;

public interface IUserRepository extends IRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);

}

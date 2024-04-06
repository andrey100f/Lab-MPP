package ro.mpp2024.concertpro.dao.repository.user_repository;

import ro.mpp2024.concertpro.dao.model.User;
import ro.mpp2024.concertpro.dao.repository.IRepository;

public interface IUserRepository extends IRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);
}

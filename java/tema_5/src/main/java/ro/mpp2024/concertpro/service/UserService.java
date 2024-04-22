package ro.mpp2024.concertpro.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2024.concertpro.dao.exception.ValidationException;
import ro.mpp2024.concertpro.dao.model.User;
import ro.mpp2024.concertpro.dao.repository.user_repository.IUserRepository;

public class UserService {
    private final IUserRepository userRepository;
    private static final Logger logger = LogManager.getLogger();

    public UserService(IUserRepository userRepository) {
        logger.info("Initializing User service.");
        this.userRepository = userRepository;
    }

    public void loginUser(String username, String password) {
        logger.traceEntry("Entering user log in function");

        User user = this.userRepository.findByUsernameAndPassword(username, password);

        if(user.getId() == 0) {
            throw new ValidationException("Wrong username or password!!");
        }

        user.setLoggedIn(true);
        this.userRepository.update(user, user.getId());

        logger.trace("User logged in.");
        logger.traceExit();
    }
}

package concertpro.network.dto;

import concertpro.model.User;

public class DTOUtils {

    public static User getFromDTO(UserDTO userDTO) {
        Long userId = userDTO.getUserId();
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        boolean loggedIn = userDTO.getLoggedIn();

        User user = new User(username, password, loggedIn);
        user.setId(userId);

        return user;
    }

    public static UserDTO getDTO(User user) {
        Long userId = user.getId();
        String username = user.getUsername();
        String password = user.getPassword();
        boolean loggedIn = user.getLoggedIn();

        return new UserDTO(userId, username, password, loggedIn);
    }

    public static UserDTO[] getDTO(User[] users) {
        UserDTO[] userDTOs = new UserDTO[users.length];

        for(int i = 0; i < users.length; i++) {
            userDTOs[i] = getDTO(users[i]);
        }

        return userDTOs;
    }

    public static User[] getFromDTO(UserDTO[] userDTOs) {
        User[] users = new User[userDTOs.length];

        for(int i = 0; i < userDTOs.length; i++) {
            users[i] = getFromDTO(userDTOs[i]);
        }

        return users;
    }

}

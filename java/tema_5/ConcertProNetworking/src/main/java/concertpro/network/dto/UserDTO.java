package concertpro.network.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private Long userId;
    private String username;
    private String password;
    private Boolean loggedIn;

    public UserDTO(Long userId, String username, String password, Boolean loggedIn) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.loggedIn = loggedIn;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

}

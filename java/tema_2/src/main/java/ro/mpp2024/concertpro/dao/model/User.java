package ro.mpp2024.concertpro.dao.model;

import java.io.Serializable;
import java.util.Objects;

public class User implements Entity<Long>, Serializable {
    private Long userId;
    private String username;
    private String password;
    private Boolean loggedIn;

    public User() {
        this.userId = 0L;
        this.username = "";
        this.password = "";
        this.loggedIn = false;
    }

    public User(String username, String password, Boolean loggedIn) {
        this.username = username;
        this.password = password;
        this.loggedIn = false;
    }

    @Override
    public Long getId() {
        return this.userId;
    }

    @Override
    public void setId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getLoggedIn() {
        return this.loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(this.userId, user.userId) && Objects.equals(this.username, user.username) &&
                Objects.equals(this.password, user.password) && Objects.equals(this.loggedIn, user.loggedIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userId, this.username, this.password, this.loggedIn);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + this.userId +
                ", username='" + this.username + '\'' +
                ", password='" + this.password + '\'' +
                ", loggedIn='" + this.loggedIn + '\'' +
                '}';
    }
}

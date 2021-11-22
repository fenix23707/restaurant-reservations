package by.vsu.model;

import java.util.Objects;

public class User {
    private Integer id;

    /** User login. Should be unique. */
    private String login;

    private String password;

    private Role role;

    private Status status;

    public User() {    }

    public User(String login, String password, Role role, Status status) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public User(Integer id, String login, String password, Role role, Status status) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && role == user.role && status == user.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, role, status);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", status=" + status +
                '}';
    }

    public enum Role {
        VISITOR, ADMIN, MANAGER
    }

    public enum Status {
        ACTIVE, INACTIVE
    }
}

package by.vsu.model;

import java.util.List;
import java.util.Objects;

public class Restaurant {
    private Integer id;

    /** Name of restaurant. Should be unique. */
    private String name;

    private String avatar;

    private String address;

    /** Owner of a restaurant. */
    private User user;

    public Restaurant() {}

    public Restaurant(String name, String avatar, String address, User user) {
        this.name = name;
        this.avatar = avatar;
        this.address = address;
        this.user = user;
    }

    public Restaurant(Integer id, String name, String avatar, String address, User user) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.address = address;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(avatar, that.avatar) && Objects.equals(address, that.address) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, avatar, address, user);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", address='" + address + '\'' +
                ", user=" + user +
                '}';
    }
}

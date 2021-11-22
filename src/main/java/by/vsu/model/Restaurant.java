package by.vsu.model;

import java.util.List;
import java.util.Objects;

public class Restaurant {
    private Integer id;

    /** Name of restaurant. Should be unique. */
    private String name;

    private String avatar;

    private String address;

    private Scheme scheme;

    /** Owner of a restaurant. */
    private User user;

    private List<Review> reviews;

    public Restaurant() {}

    public Restaurant(String name, String avatar, String address, Scheme scheme, User user, List<Review> reviews) {
        this.name = name;
        this.avatar = avatar;
        this.address = address;
        this.scheme = scheme;
        this.user = user;
        this.reviews = reviews;
    }

    public Restaurant(Integer id, String name, String avatar, String address, Scheme scheme, User user, List<Review> reviews) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.address = address;
        this.scheme = scheme;
        this.user = user;
        this.reviews = reviews;
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

    public Scheme getScheme() {
        return scheme;
    }

    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
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
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(avatar, that.avatar) && Objects.equals(address, that.address) && Objects.equals(scheme, that.scheme) && Objects.equals(user, that.user) && Objects.equals(reviews, that.reviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, avatar, address, scheme, user, reviews);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", adres='" + address + '\'' +
                ", scheme=" + scheme +
                ", user=" + user +
                ", reviews=" + reviews +
                '}';
    }
}

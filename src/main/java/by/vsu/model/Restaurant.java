package by.vsu.model;

import java.util.Objects;

public class Restaurant {
    private Integer id;

    private String name;

    private String avatar;

    private String adres;

    private User user;

    public Restaurant() {}

    public Restaurant(Integer id, String name, String avatar, String adres, User user) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.adres = adres;
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

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
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
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(avatar, that.avatar) && Objects.equals(adres, that.adres) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, avatar, adres, user);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", adres='" + adres + '\'' +
                ", user=" + user +
                '}';
    }
}

package by.vsu.model;

import java.util.Date;
import java.util.Objects;

/**
 * Represents a review for a specific restaurant.
 */
public class Review {
    private Integer id;

    /** Number from 1 to 10 */
    private int rate;

    /** Comment that leave user. */
    private String text;

    private Date date = new Date();

    /** User who left this review. */
    private User user;

    private Restaurant restaurant;

    public Review() {    }

    public Review(Integer id) {
        this.id = id;
    }

    public Review(int rate, String text, Date date, User user, Restaurant restaurant) {
        this.rate = rate;
        this.text = text;
        this.date = date;
        this.user = user;
        this.restaurant = restaurant;
    }

    public Review(Integer id, int rate, String text, Date date, User user, Restaurant restaurant) {
        this.id = id;
        this.rate = rate;
        this.text = text;
        this.date = date;
        this.user = user;
        this.restaurant = restaurant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review1 = (Review) o;
        return rate == review1.rate && Objects.equals(id, review1.id) && Objects.equals(text, review1.text) && Objects.equals(date, review1.date) && Objects.equals(user, review1.user) && Objects.equals(restaurant, review1.restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rate, text, date, user, restaurant);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", rate=" + rate +
                ", review='" + text + '\'' +
                ", date=" + date +
                ", user=" + user +
                ", restaurant=" + restaurant +
                '}';
    }
}

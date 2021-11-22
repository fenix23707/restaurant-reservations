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
    private String review;

    private Date date;

    /** User who left this review. */
    private User user;

    public Review() {    }

    public Review(int rate, String review, Date date, User user) {
        this.rate = rate;
        this.review = review;
        this.date = date;
        this.user = user;
    }

    public Review(Integer id, int rate, String review, Date date, User user) {
        this.id = id;
        this.rate = rate;
        this.review = review;
        this.date = date;
        this.user = user;
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

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review1 = (Review) o;
        return rate == review1.rate && Objects.equals(id, review1.id) && Objects.equals(review, review1.review) && Objects.equals(date, review1.date) && Objects.equals(user, review1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rate, review, date, user);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", rate=" + rate +
                ", review='" + review + '\'' +
                ", date=" + date +
                ", user=" + user +
                '}';
    }
}

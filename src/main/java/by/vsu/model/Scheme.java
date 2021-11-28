package by.vsu.model;

import java.util.Objects;

/**
 * Represent a scheme of a restaurant.
 */
public class Scheme {
    private Integer id;

    /** A width of a restaurant in meters. */
    private int width;

    /** A height of a restaurant in meters. */
    private int height;

    private Restaurant restaurant;

    public Scheme() {}

    public Scheme(Integer id) {
        this.id = id;
    }

    public Scheme(int width, int height, Restaurant restaurant) {
        this.width = width;
        this.height = height;
        this.restaurant = restaurant;
    }

    public Scheme(Integer id, int width, int height, Restaurant restaurant) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.restaurant = restaurant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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
        Scheme scheme = (Scheme) o;
        return width == scheme.width && height == scheme.height && Objects.equals(id, scheme.id) && Objects.equals(restaurant, scheme.restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, width, height, restaurant);
    }

    @Override
    public String toString() {
        return "Scheme{" +
                "id=" + id +
                ", width=" + width +
                ", height=" + height +
                ", restaurant=" + restaurant +
                '}';
    }
}

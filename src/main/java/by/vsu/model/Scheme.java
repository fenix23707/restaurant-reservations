package by.vsu.model;

import java.util.List;
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

    /** All tables associated with this scheme. */
    private List<Table> tables;

    public Scheme() {}

    public Scheme(int width, int height, List<Table> tables) {
        this.width = width;
        this.height = height;
        this.tables = tables;
    }

    public Scheme(Integer id, int width, int height, List<Table> tables) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.tables = tables;
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

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scheme scheme = (Scheme) o;
        return width == scheme.width && height == scheme.height && Objects.equals(id, scheme.id) && Objects.equals(tables, scheme.tables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, width, height, tables);
    }

    @Override
    public String toString() {
        return "Scheme{" +
                "id=" + id +
                ", width=" + width +
                ", height=" + height +
                ", tables=" + tables +
                '}';
    }
}

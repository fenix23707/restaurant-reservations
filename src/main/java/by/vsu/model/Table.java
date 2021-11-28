package by.vsu.model;

import java.util.Objects;

public class Table {
    private Integer id;

    /** Max number of people can be placed at a table. */
    private int capacity;

    /** A width of a restaurant in centimetres */
    private int width;

    /** A height of a restaurant in centimetres */
    private int height;

    /** Location of a table within a scheme. */
    private Position position;

    private Scheme scheme;

    public Table() {}

    public Table(Integer id) {
        this.id = id;
    }

    public Table(int capacity, int width, int height, Position position, Scheme scheme) {
        this.capacity = capacity;
        this.width = width;
        this.height = height;
        this.position = position;
        this.scheme = scheme;
    }

    public Table(Integer id, int capacity, int width, int height, Position position, Scheme scheme) {
        this.id = id;
        this.capacity = capacity;
        this.width = width;
        this.height = height;
        this.position = position;
        this.scheme = scheme;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Scheme getScheme() {
        return scheme;
    }

    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return capacity == table.capacity && width == table.width && height == table.height && Objects.equals(id, table.id) && Objects.equals(position, table.position) && Objects.equals(scheme, table.scheme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, capacity, width, height, position, scheme);
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", width=" + width +
                ", height=" + height +
                ", position=" + position +
                ", scheme=" + scheme +
                '}';
    }
}

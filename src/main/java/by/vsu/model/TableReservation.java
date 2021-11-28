package by.vsu.model;

import java.util.Date;
import java.util.Objects;

/**
 * Store information about table reservations.
 */
public class TableReservation {
    private Integer id;

    private Date begin;

    private Date end;

    /** Actual number of people placed at a table. */
    private int capacity;

    private Status status;

    /** Information about table that have been booked. */
    private Table table;

    /** User who booked this table. */
    private User user;

    public TableReservation() {    }

    public TableReservation(Date begin, Date end, int capacity, Status status, Table table, User user) {
        this.begin = begin;
        this.end = end;
        this.capacity = capacity;
        this.status = status;
        this.table = table;
        this.user = user;
    }

    public TableReservation(Integer id, Date begin, Date end, int capacity, Status status, Table table, User user) {
        this.id = id;
        this.begin = begin;
        this.end = end;
        this.capacity = capacity;
        this.status = status;
        this.table = table;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public enum Status {
        BOOKED, COMPLETED, CANCELLED
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableReservation that = (TableReservation) o;
        return capacity == that.capacity && Objects.equals(id, that.id) && Objects.equals(begin, that.begin) && Objects.equals(end, that.end) && status == that.status && Objects.equals(table, that.table) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, begin, end, capacity, status, table, user);
    }

    @Override
    public String toString() {
        return "TableReservation{" +
                "id=" + id +
                ", begin=" + begin +
                ", end=" + end +
                ", capacity=" + capacity +
                ", status=" + status +
                ", table=" + table +
                ", user=" + user +
                '}';
    }
}

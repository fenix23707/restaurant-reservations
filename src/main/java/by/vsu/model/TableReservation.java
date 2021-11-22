package by.vsu.model;

import java.util.Date;

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
}

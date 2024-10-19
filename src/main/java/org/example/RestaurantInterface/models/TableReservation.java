package org.example.RestaurantInterface.models;

import java.sql.Timestamp;

public class TableReservation {
    private int id;
    private int table_id;
    private Timestamp start_time;
    private Timestamp end_time;

    public TableReservation(int id, int table_id, Timestamp start_time, Timestamp end_time) {
        this.id = id;
        this.table_id = table_id;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public TableReservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public Timestamp getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }
}

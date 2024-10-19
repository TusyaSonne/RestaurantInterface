package org.example.RestaurantInterface.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Table {

    private int id;

    private int number_of_seats;

    public Table(int id, int number_of_seats) {
        this.id = id;
        this.number_of_seats = number_of_seats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber_of_seats() {
        return number_of_seats;
    }

    public void setNumber_of_seats(int number_of_seats) {
        this.number_of_seats = number_of_seats;
    }

    public Table() {
    }
}

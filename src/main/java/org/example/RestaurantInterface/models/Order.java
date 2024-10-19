package org.example.RestaurantInterface.models;

import java.sql.Timestamp;
import java.util.List;

public class Order {

    private int id;
    private double total_amount;
    private Timestamp order_time;
    private String status_of_order;
    private int table_id;
    private int client_id;

    private List<Integer> DishesId;

    public Order(int id, double total_amount, Timestamp order_time, String status_of_order, int table_id, int client_id, List<Integer> DishesId) {
        this.id = id;
        this.total_amount = total_amount;
        this.order_time = order_time;
        this.status_of_order = status_of_order;
        this.table_id = table_id;
        this.client_id = client_id;
        this.DishesId = DishesId;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public Timestamp getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Timestamp order_time) {
        this.order_time = order_time;
    }

    public String getStatus_of_order() {
        return status_of_order;
    }

    public void setStatus_of_order(String status_of_order) {
        this.status_of_order = status_of_order;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public List<Integer> getDishesId() {
        return DishesId;
    }

    public void setDishesId(List<Integer> dishesId) {
        DishesId = dishesId;
    }
}

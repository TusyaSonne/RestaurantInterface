package org.example.RestaurantInterface.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class Client {

    private int id;


    @NotEmpty(message = "ФИО не может быть пустым")
    @Pattern(regexp = "[А-Я][а-я]+ [А-Я][а-я]+ [А-Я][а-я]+", message = "Введите корректное ФИО (Иванов Иван Иванович")
    private String full_name;


    @NotEmpty
    @Pattern(regexp = "^7\\d{10}$", message = "Введите телефон в виде 78005553535")
    private String phone_number;

    public Client(int id, String full_name, String phone_number) {
        this.id = id;
        this.full_name = full_name;
        this.phone_number = phone_number;
    }

    public Client() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }


    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}

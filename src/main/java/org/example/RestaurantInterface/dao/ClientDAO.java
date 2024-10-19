package org.example.RestaurantInterface.dao;


import org.example.RestaurantInterface.models.Client;
import org.example.RestaurantInterface.models.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ClientDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ClientDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ClientDAO() {
    }

    public List<Client> index() {
        return jdbcTemplate.query("SELECT * FROM client", new BeanPropertyRowMapper<>(Client.class));
    }

    //Метод для валидатора
    public Optional<Client> show(String full_name) {
        return jdbcTemplate.query("SELECT * FROM client WHERE full_name=?", new Object[]{full_name}, new BeanPropertyRowMapper<>(Client.class))
                .stream().findAny();// ПРОВЕРИТЬ ЧТО ПОЛЯ СОВПАДАЮТ ПО ИМЕНИ В ПОЛЯМИ В ТАБЛИЦЕ
    }

    public Client show(int id) {
        return jdbcTemplate.query("SELECT * FROM client WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Client.class))
                .stream().findAny().orElse(null);// ПРОВЕРИТЬ ЧТО ПОЛЯ СОВПАДАЮТ ПО ИМЕНИ В ПОЛЯМИ В ТАБЛИЦЕ
    }

    public void save(Client client) {
        jdbcTemplate.update("INSERT INTO client(full_name, phone_number) VALUES(?,?)", client.getFull_name(), client.getPhone_number());
    }

    public void update(int id, Client updatedClient) {
        jdbcTemplate.update("UPDATE client SET full_name = ?, phone_number = ? WHERE id = ?", updatedClient.getFull_name(), updatedClient.getPhone_number(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM client WHERE id = ?", id);
    }

//    public List<Table> getReadedBooks(int id) {
//        return jdbcTemplate.query("SELECT * FROM table WHERE person_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Table.class));
//    }
}

package org.example.RestaurantInterface.dao;


import org.example.RestaurantInterface.models.Table;
import org.example.RestaurantInterface.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TableDAO {
    private JdbcTemplate jdbcTemplate;
    private final ClientDAO clientDAO;

    @Autowired
    public TableDAO(JdbcTemplate jdbcTemplate, ClientDAO clientDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.clientDAO = clientDAO;
    }

    public List<Table> index() {
        return jdbcTemplate.query("SELECT * FROM \"table\"", new BeanPropertyRowMapper<>(Table.class));
    }

    public Table show(int id) {
        return jdbcTemplate.query("SELECT * FROM \"table\" WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Table.class))
                .stream().findAny().orElse(null);
    }

    public void save(Table table) {
        jdbcTemplate.update("INSERT INTO \"table\"(number_of_seats) VALUES(?)", table.getNumber_of_seats());
    }

    public void update(int id, Table table) {
        jdbcTemplate.update("UPDATE \"table\" SET number_of_seats = ? WHERE id = ?", table.getNumber_of_seats(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM \"table\" WHERE id = ?", id);
    }

//    public Optional<Client> getBookOwner(int id) {
//        return jdbcTemplate.query("SELECT client.* FROM table JOIN client ON table.person_id=person.id WHERE book.id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Client.class))
//                .stream().findAny();
//    }

//    public void assign(int personId, int bookId) {
//        jdbcTemplate.update("UPDATE book SET person_id = ? WHERE id = ?", personId, bookId);
//    }
//
//    public void release(int bookId) {
//        jdbcTemplate.update("UPDATE book SET person_id = null WHERE id = ?", bookId);
//    }
}

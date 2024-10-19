package org.example.RestaurantInterface.dao;

import org.example.RestaurantInterface.models.Dish;
import org.example.RestaurantInterface.models.Ingredient;
import org.example.RestaurantInterface.models.Table;
import org.example.RestaurantInterface.models.TableReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Component
public class TableReservationDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TableReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public TableReservationDAO() {
    }

    public List<TableReservation> index() {
        return jdbcTemplate.query("SELECT * FROM table_reservation", new BeanPropertyRowMapper<>(TableReservation.class));
    }

    public TableReservation show(int id) {
        return jdbcTemplate.query("SELECT * FROM table_reservation WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(TableReservation.class))
                .stream().findAny().orElse(null);// ПРОВЕРИТЬ ЧТО ПОЛЯ СОВПАДАЮТ ПО ИМЕНИ В ПОЛЯМИ В ТАБЛИЦЕ
    }

    public void save(TableReservation tableReservation) {
        jdbcTemplate.update("INSERT INTO table_reservation(table_id, start_time, end_time) VALUES(?,?,?)", tableReservation.getTable_id(), tableReservation.getStart_time(), tableReservation.getEnd_time());
    }

    public void update(int id, TableReservation updatedTableReservation) {
        jdbcTemplate.update("UPDATE table_reservation SET table_id = ?, start_time = ?, end_time = ? WHERE id = ?", updatedTableReservation.getTable_id(), updatedTableReservation.getStart_time(), updatedTableReservation.getEnd_time(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM table_reservation WHERE id = ?", id);
    }

    public List<Map<String, Object>> getAvailableTables(Timestamp startTime, Timestamp endTime) {
        return jdbcTemplate.queryForList("SELECT * FROM get_available_tables(?, ?)", startTime, endTime); //ИСПОЛЬЗОВАНИЕ ФУНКЦИИ get_available_tables
    }

    public boolean isTableAvailable(int tableId, Timestamp startTime, Timestamp endTime) {
        List<Map<String, Object>> availableTables = jdbcTemplate.queryForList(
                "SELECT * FROM get_available_tables(?, ?) WHERE table_id = ?",
                startTime, endTime, tableId);
        return !availableTables.isEmpty();
    }


}

package org.example.RestaurantInterface.dao;

import org.example.RestaurantInterface.models.Dish;
import org.example.RestaurantInterface.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component
public class OrderDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public OrderDAO() {
    }

    public List<Order> index() {
        return jdbcTemplate.query("SELECT * FROM \"order\"", new BeanPropertyRowMapper<>(Order.class));
    }

    //Метод для валидатора
    public Optional<Order> show(String full_name) {
        return jdbcTemplate.query("SELECT * FROM \"order\" WHERE full_name=?", new Object[]{full_name}, new BeanPropertyRowMapper<>(Order.class))
                .stream().findAny();// ПРОВЕРИТЬ ЧТО ПОЛЯ СОВПАДАЮТ ПО ИМЕНИ В ПОЛЯМИ В ТАБЛИЦЕ
    }

    public Order show(int id) {
        return jdbcTemplate.query("SELECT * FROM \"order\" WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Order.class))
                .stream().findAny().orElse(null);// ПРОВЕРИТЬ ЧТО ПОЛЯ СОВПАДАЮТ ПО ИМЕНИ В ПОЛЯМИ В ТАБЛИЦЕ
    }

    public void save(Order order) {

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        //jdbcTemplate.update("INSERT INTO \"order\"(total_amount, order_time, status_of_order, table_id, client_id) VALUES(?,?,?,?,?)", order.getTotal_amount(), currentTimestamp, order.getStatus_of_order(), order.getTable_id(), order.getClient_id());
        jdbcTemplate.update("CALL add_new_order(?,?,?,?,?)", order.getTotal_amount(), currentTimestamp, order.getStatus_of_order(), order.getTable_id(), order.getClient_id()); //ПРОЦЕДУРА РАБОТАЕТ
        // Получение id сохраненного заказа
        int orderId = jdbcTemplate.queryForObject("SELECT lastval()", Integer.class);

        // Сохранение блюд, связанных с заказом
        for (int dishId : order.getDishesId()) {
            jdbcTemplate.update("INSERT INTO order_dish(order_id, dish_id) VALUES (?, ?)", orderId, dishId);
        }
    }

    public void update(int id, Order updatedOrder) {

        if (updatedOrder.getOrder_time() == null) {
            updatedOrder.setOrder_time(new Timestamp(System.currentTimeMillis()));
        }

        jdbcTemplate.update("UPDATE \"order\" SET total_amount = ?, order_time = ?, status_of_order = ?, table_id = ?, client_id = ? WHERE id = ?", updatedOrder.getTotal_amount(), updatedOrder.getOrder_time(), updatedOrder.getStatus_of_order(), updatedOrder.getTable_id(), updatedOrder.getClient_id(), id);

        // Добавление новых записей о блюдах
        for (int dishId : updatedOrder.getDishesId()) {
            jdbcTemplate.update("INSERT INTO order_dish(order_id, dish_id) VALUES (?, ?)", id, dishId);
        }
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM \"order\" WHERE id = ?", id);
    }

    public List<Dish> getDishesByOrder(int id) {
        return jdbcTemplate.query("SELECT Dish.name FROM \"order\" JOIN order_dish ON \"order\".id=order_dish.order_id JOIN Dish ON order_dish.dish_id = Dish.id  WHERE \"order\".id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Dish.class));
    }
}

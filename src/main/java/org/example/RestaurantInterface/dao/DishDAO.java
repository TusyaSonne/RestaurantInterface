package org.example.RestaurantInterface.dao;

import org.example.RestaurantInterface.models.Client;
import org.example.RestaurantInterface.models.Dish;
import org.example.RestaurantInterface.models.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DishDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DishDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DishDAO() {
    }

    public List<Dish> index() {
        return jdbcTemplate.query("SELECT * FROM dish", new BeanPropertyRowMapper<>(Dish.class));
    }

    public Dish show(int id) {
        return jdbcTemplate.query("SELECT * FROM dish WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Dish.class))
                .stream().findAny().orElse(null);// ПРОВЕРИТЬ ЧТО ПОЛЯ СОВПАДАЮТ ПО ИМЕНИ В ПОЛЯМИ В ТАБЛИЦЕ
    }

    public void save(Dish dish) {
        jdbcTemplate.update("INSERT INTO Dish(name, price) VALUES(?,?)", dish.getName(), dish.getPrice());
    }

    public void update(int id, Dish updatedDish) {
        jdbcTemplate.update("UPDATE dish SET name = ?, price = ? WHERE id = ?", updatedDish.getName(), updatedDish.getPrice(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM dish WHERE id = ?", id);
    }

    public List<Ingredient> getIngredientsByDish(int id) {
        return jdbcTemplate.query("SELECT ingredient.name FROM Dish JOIN Dish_Ingredient ON Dish.id=Dish_Ingredient.dish_id JOIN Ingredient on Dish_Ingredient.ingredient_id=Ingredient.id  WHERE Dish.id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Ingredient.class));
    }
}

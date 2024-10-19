package org.example.RestaurantInterface.dao;

import org.example.RestaurantInterface.models.Dish;
import org.example.RestaurantInterface.models.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IngredientDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public IngredientDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public IngredientDAO() {
    }

    public List<Ingredient> index() {
        return jdbcTemplate.query("SELECT * FROM ingredient", new BeanPropertyRowMapper<>(Ingredient.class));
    }

    public Ingredient show(int id) {
        return jdbcTemplate.query("SELECT * FROM ingredient WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Ingredient.class))
                .stream().findAny().orElse(null);// ПРОВЕРИТЬ ЧТО ПОЛЯ СОВПАДАЮТ ПО ИМЕНИ В ПОЛЯМИ В ТАБЛИЦЕ
    }

    public void save(Ingredient ingredient) {
        jdbcTemplate.update("INSERT INTO ingredient(name, quantity) VALUES(?,?)", ingredient.getName(), ingredient.getQuantity());
    }

    public void update(int id, Ingredient updatedIngredient) {
        jdbcTemplate.update("UPDATE ingredient SET name = ?, quantity = ? WHERE id = ?", updatedIngredient.getName(), updatedIngredient.getQuantity(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM ingredient WHERE id = ?", id);
    }


    public List<Ingredient> search(String name, boolean filterByQuantity, List<Integer> dishesId) {
        //String query = "SELECT * FROM ingredient WHERE name LIKE ?";
        //Object[] args = new Object[]{"%" + name + "%"};

        String query = "SELECT DISTINCT ingredient.* FROM ingredient " +
                "LEFT JOIN Dish_Ingredient ON ingredient.id = Dish_Ingredient.ingredient_id " +
                "LEFT JOIN dish ON Dish_Ingredient.dish_id = dish.id " +
                "WHERE ingredient.name LIKE ?";
        Object[] args = new Object[]{"%" + name + "%"};

        if (filterByQuantity) {
            query += " ORDER BY quantity DESC";
        }

        if (dishesId != null && !dishesId.isEmpty()) {
            query += " AND dish.id IN (" + String.join(",", dishesId.stream().map(String::valueOf).toArray(String[]::new)) + ")";
        }
        return jdbcTemplate.query(query, args, new BeanPropertyRowMapper<>(Ingredient.class));
    }
}

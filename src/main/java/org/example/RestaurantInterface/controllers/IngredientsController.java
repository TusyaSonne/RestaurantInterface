package org.example.RestaurantInterface.controllers;

import jakarta.validation.Valid;
import org.example.RestaurantInterface.dao.DishDAO;
import org.example.RestaurantInterface.dao.IngredientDAO;
import org.example.RestaurantInterface.models.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ingredients")
public class IngredientsController {

    private final IngredientDAO ingredientDAO;
    private final DishDAO dishDAO;

    @Autowired
    public IngredientsController(IngredientDAO ingredientDAO, DishDAO dishDAO) {
        this.ingredientDAO = ingredientDAO;
        this.dishDAO = dishDAO;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("ingredients", ingredientDAO.index());
        return "ingredients/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) { //запомнить синтаксис
        model.addAttribute("ingredient", ingredientDAO.show(id));
        return "ingredients/show";
    }

    @GetMapping("/new")
    public String newIngredient(@ModelAttribute("ingredient") Ingredient ingredient) {
        return "ingredients/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("ingredient") @Valid Ingredient ingredient, BindingResult bindingResult) {

//        personValidator.validate(client, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return "people/new";
//        }

        ingredientDAO.save(ingredient);
        return "redirect:/ingredients";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("ingredient", ingredientDAO.show(id));
        return "ingredients/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("ingredient") @Valid Ingredient ingredient, BindingResult bindingResult, @PathVariable("id") int id) {

//        personValidator.validate(client, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return "people/edit";
//        }
        ingredientDAO.update(id, ingredient);
        return "redirect:/ingredients";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        ingredientDAO.delete(id);
        return "redirect:/ingredients";
    }


    @GetMapping("/search")
    public String searchPage(Model model) {
        model.addAttribute("dishes", dishDAO.index());
        return "ingredients/search";
    }

    // метод для обработки поискового запроса
    @PostMapping("/search")
    public String searchResults(@RequestParam("name") String name,
                                @RequestParam(value = "filterByQuantity", required = false) boolean filterByQuantity,
                                @RequestParam(value = "dishesID", required = false) List<Integer> dishesID,
                                Model model) {
        List<Ingredient> ingredients = ingredientDAO.search(name, filterByQuantity, dishesID);
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("dishes", dishDAO.index());
        return "ingredients/search";
    }
}

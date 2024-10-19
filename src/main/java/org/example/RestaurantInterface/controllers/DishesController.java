package org.example.RestaurantInterface.controllers;

import jakarta.validation.Valid;

import org.example.RestaurantInterface.dao.DishDAO;
import org.example.RestaurantInterface.models.Dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/dishes")
public class DishesController {

    private final DishDAO dishDAO;

    @Autowired
    public DishesController(DishDAO dishDAO) {
        this.dishDAO = dishDAO;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("dishes", dishDAO.index());
        return "dishes/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) { //запомнить синтаксис
        model.addAttribute("dish", dishDAO.show(id));
        model.addAttribute("ingredients", dishDAO.getIngredientsByDish(id));
        return "dishes/show";
    }

    @GetMapping("/new")
    public String newDish(@ModelAttribute("dish") Dish dish) {
        return "dishes/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("dish") @Valid Dish dish, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "dishes/new";
        }

        dishDAO.save(dish);
        return "redirect:/dishes";
    }


    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("dish", dishDAO.show(id));
        return "dishes/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("dish") @Valid Dish dish, BindingResult bindingResult, @PathVariable("id") int id) {

//        personValidator.validate(client, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return "people/edit";
//        }
        dishDAO.update(id, dish);
        return "redirect:/dishes";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        dishDAO.delete(id);
        return "redirect:/dishes";
    }



}

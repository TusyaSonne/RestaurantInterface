package org.example.RestaurantInterface.controllers;

import jakarta.validation.Valid;
import org.example.RestaurantInterface.dao.DishDAO;
import org.example.RestaurantInterface.dao.OrderDAO;
import org.example.RestaurantInterface.models.Order;
import org.example.RestaurantInterface.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    private final OrderDAO orderDAO;
    private final DishDAO dishDAO;

    @Autowired
    public OrdersController(OrderDAO orderDAO, DishDAO dishDAO) {
        this.orderDAO = orderDAO;
        this.dishDAO = dishDAO;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("orders", orderDAO.index());
        return "orders/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) { //запомнить синтаксис
        model.addAttribute("order", orderDAO.show(id));
        model.addAttribute("dishes", orderDAO.getDishesByOrder(id));
        return "orders/show";
    }

    @GetMapping("/new")
    public String newOrder(@ModelAttribute("order") Order order, Model model) {
        model.addAttribute("dishes", dishDAO.index());
        return "orders/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult) {

//        personValidator.validate(client, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return "people/new";
//        }

        orderDAO.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {

        Order order = orderDAO.show(id);
        // Проверяем, что order.dishIds не null
        if (order.getDishesId() == null) {
            order.setDishesId(new ArrayList<>());  // Инициализируем пустым списком
        }

        model.addAttribute("order", orderDAO.show(id));
        model.addAttribute("dishes", dishDAO.index());
        return "orders/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult, @PathVariable("id") int id) {

//        personValidator.validate(client, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return "people/edit";
//        }
        orderDAO.update(id, order);
        return "redirect:/orders";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        orderDAO.delete(id);
        return "redirect:/orders";
    }
}

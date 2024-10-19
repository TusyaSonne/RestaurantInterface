package org.example.RestaurantInterface.controllers;

import jakarta.validation.Valid;
import org.example.RestaurantInterface.dao.ClientDAO;
import org.example.RestaurantInterface.models.Client;
import org.example.RestaurantInterface.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clients")
public class ClientsController {

    private final ClientDAO clientDAO;
    private final PersonValidator personValidator;


    @Autowired
    public ClientsController(ClientDAO clientDAO, PersonValidator personValidator) {
        this.clientDAO = clientDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("clients", clientDAO.index());
        return "clients/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) { //запомнить синтаксис
        model.addAttribute("client", clientDAO.show(id));
        //model.addAttribute("books", clientDAO.getReadedBooks(id));
        return "clients/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("client") Client client) {
        return "clients/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult) {

//        personValidator.validate(client, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return "people/new";
//        }

        clientDAO.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("client", clientDAO.show(id));
        return "clients/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult, @PathVariable("id") int id) {

//        personValidator.validate(client, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return "people/edit";
//        }
        clientDAO.update(id, client);
        return "redirect:/clients";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        clientDAO.delete(id);
        return "redirect:/clients";
    }
}


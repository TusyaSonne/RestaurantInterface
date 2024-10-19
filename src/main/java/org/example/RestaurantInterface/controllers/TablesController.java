package org.example.RestaurantInterface.controllers;

import jakarta.validation.Valid;
import org.example.RestaurantInterface.dao.TableDAO;
import org.example.RestaurantInterface.dao.ClientDAO;
import org.example.RestaurantInterface.models.Client;
import org.example.RestaurantInterface.models.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tables")
public class TablesController {

    private final TableDAO tableDAO;


    @Autowired
    public TablesController(TableDAO tableDAO) {
        this.tableDAO = tableDAO;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("tables", tableDAO.index());

        return "tables/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("client") Client client) {
        model.addAttribute("table", tableDAO.show(id));
//        Optional<Client> tableOwner = tableDAO.getBookOwner(id);
//
//        if (tableOwner.isPresent()) {
//            model.addAttribute("owner", tableOwner.get());
//        } else {
//            model.addAttribute("people", clientDAO.index());
//        }

        return "tables/show";
    }

    @GetMapping("/new")
    public String newTable(@ModelAttribute("table") Table table) { //если мы используем thymeleaf-формы, им необходимо передавать тот объект, для которого эта форма нужна
        return "tables/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("table") @Valid Table table, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "tables/new";
        }

        tableDAO.save(table);
        return "redirect:/tables";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("table", tableDAO.show(id));
        return "tables/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("table") @Valid Table table, BindingResult bindingResult, @PathVariable("id") int id) { //ВСЕГДА после Valid BindingResult

        if (bindingResult.hasErrors()) {
            return "tables/edit";
        }

        tableDAO.update(id, table);
        return "redirect:/tables";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        tableDAO.delete(id);
        return "redirect:/tables";
    }


//    @PatchMapping("/{id}/assign")
//    public String assignBook(@PathVariable("id") int book_id, @ModelAttribute("person") Client client) {
//        tableDAO.assign(client.getId(), book_id);
//        return "redirect:/tables";
//    }
//
//    @PatchMapping("/{id}/release")
//    public String releaseBook(@PathVariable("id") int book_id) {
//        tableDAO.release(book_id);
//        return "redirect:/tables";
//    }

}

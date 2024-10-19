package org.example.RestaurantInterface.controllers;

import jakarta.validation.Valid;

import org.example.RestaurantInterface.dao.TableReservationDAO;
import org.example.RestaurantInterface.models.TableReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/table_reservations")
public class TableReservationsController {
    private final TableReservationDAO tableReservationDAO;

    @Autowired
    public TableReservationsController(TableReservationDAO tableReservationDAO) {
        this.tableReservationDAO = tableReservationDAO;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("table_reservations", tableReservationDAO.index());
        return "table_reservations/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) { //запомнить синтаксис
        model.addAttribute("table_reservation", tableReservationDAO.show(id));
        //model.addAttribute("ingredients", tableReservationDAO.getIngredientsByDish(id));
        return "table_reservations/show";
    }

    @GetMapping("/new")
    public String newTableReservation(@ModelAttribute("table_reservation") TableReservation tableReservation) {
        return "table_reservations/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("table_reservation") @Valid TableReservation tableReservation, BindingResult bindingResult) {

        // Проверка доступности стола
        if (!tableReservationDAO.isTableAvailable(tableReservation.getTable_id(),
                tableReservation.getStart_time(), tableReservation.getEnd_time())) {
            bindingResult.rejectValue("table_id", "error.table_reservation", "Этот стол уже занят в выбранное время.");
        }

        if (bindingResult.hasErrors()) {
            return "table_reservations/new";
        }

        tableReservationDAO.save(tableReservation);
        return "redirect:/table_reservations";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("table_reservation", tableReservationDAO.show(id));
        return "table_reservations/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("table_reservation") @Valid TableReservation tableReservation, BindingResult bindingResult, @PathVariable("id") int id) {

//        personValidator.validate(client, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return "people/edit";
//        }
        tableReservationDAO.update(id, tableReservation);
        return "redirect:/table_reservations";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        tableReservationDAO.delete(id);
        return "redirect:/table_reservations";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String startTime,
                         @RequestParam(required = false) String endTime,
                         Model model) {
        List<Map<String, Object>> availableTables = new ArrayList<>();

        if (startTime != null && endTime != null) {
            try {
                // Преобразование строкового формата ISO 8601 в Timestamp
                Timestamp startTimestamp = Timestamp.valueOf(startTime.replace("T", " ") + ":00");
                Timestamp endTimestamp = Timestamp.valueOf(endTime.replace("T", " ") + ":00");
                availableTables = tableReservationDAO.getAvailableTables(startTimestamp, endTimestamp);
            } catch (IllegalArgumentException e) {
                // Обработка ошибки формата
                model.addAttribute("error", "Неверный формат даты и времени. Пожалуйста, убедитесь, что вы ввели данные правильно.");
            }
        }

        model.addAttribute("availableTables", availableTables);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        return "table_reservations/search";
    }


}

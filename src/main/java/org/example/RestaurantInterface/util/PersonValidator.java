package org.example.RestaurantInterface.util;


import org.example.RestaurantInterface.dao.ClientDAO;
import org.example.RestaurantInterface.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final ClientDAO clientDAO;

    @Autowired
    public PersonValidator(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Client.class.equals(clazz); //указываем на каком классе используется валидатор
    }

    @Override
    public void validate(Object target, Errors errors) {
        Client client = (Client) target;

        if (clientDAO.show(client.getFull_name()).isPresent()) { //благодаря Optional вызываем метод isPresent
            errors.rejectValue("fullname", "", "Это имя уже занято.");
        }

    }
}

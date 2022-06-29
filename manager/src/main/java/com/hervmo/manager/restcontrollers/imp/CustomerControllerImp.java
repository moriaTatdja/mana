package com.hervmo.manager.restcontrollers.imp;

import com.hervmo.manager.models.entities.Customer;
import com.hervmo.manager.restcontrollers.CustomerController;
import org.springframework.http.ResponseEntity;

public class CustomerControllerImp implements CustomerController {
    @Override
    public ResponseEntity getCustomer(Long id) {
        return null;
    }

    @Override
    public ResponseEntity addOrEditCustomer(Customer user) {
        return null;
    }
}

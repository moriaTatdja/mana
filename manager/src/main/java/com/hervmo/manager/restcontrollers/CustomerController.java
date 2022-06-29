package com.hervmo.manager.restcontrollers;

import com.hervmo.manager.models.entities.Customer;
import com.hervmo.manager.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping(Constants.CUSTOMER_ROUTE_NAME)
public interface CustomerController {

    @GetMapping("/{id}")
    ResponseEntity getCustomer(@PathVariable() Long id);

    @PostMapping("/")
    ResponseEntity addOrEditCustomer(@RequestBody Customer user);
}

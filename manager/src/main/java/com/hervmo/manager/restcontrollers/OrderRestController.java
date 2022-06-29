package com.hervmo.manager.restcontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin
public interface OrderRestController {

    @GetMapping("/{id}")
    ResponseEntity<byte[]> downloadInvoice(@PathVariable Long id);
}

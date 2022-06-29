package com.hervmo.manager.restcontrollers;

import com.hervmo.manager.models.entities.Warehouse;
import com.hervmo.manager.models.entities.models.WarehouseGenerateModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
public interface WarehouseRestController {


    @GetMapping("/{id}")
    ResponseEntity getWarehouse(@PathVariable Long id);

    @PostMapping("/")
    ResponseEntity addWarehouse(@RequestBody WarehouseGenerateModel wgm);

}

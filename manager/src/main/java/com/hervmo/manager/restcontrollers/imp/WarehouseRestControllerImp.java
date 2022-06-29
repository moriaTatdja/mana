package com.hervmo.manager.restcontrollers.imp;

import com.hervmo.manager.models.entities.Warehouse;
import com.hervmo.manager.models.entities.models.WarehouseGenerateModel;
import com.hervmo.manager.restcontrollers.WarehouseRestController;
import com.hervmo.manager.services.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.hervmo.manager.utils.Constants.WAREHOUSE_ROUTE_NAME;

@RestController
@CrossOrigin
@RequestMapping(WAREHOUSE_ROUTE_NAME)
@Slf4j
public class WarehouseRestControllerImp implements WarehouseRestController {
    @Autowired
    private WarehouseService warehouseService;

    @Override
    public ResponseEntity<List<Warehouse>> getWarehouse(Long id) {
        if (id == null) {
            id = 0L;
        }
        return ResponseEntity.ok(warehouseService.getWarehouse(id));
    }

    @Override
    public ResponseEntity<Warehouse> addWarehouse(WarehouseGenerateModel wr) {

        // @Todo Check if WarehouseGenerateModel get some null then reply with the related missing value Error
        if (wr == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(warehouseService.addWarehouse(wr));
    }
}

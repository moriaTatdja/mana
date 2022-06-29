package com.hervmo.manager.restcontrollers.imp;


import com.hervmo.manager.restcontrollers.OrderRestController;
import com.hervmo.manager.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.hervmo.manager.utils.Constants.ORDER_ROUTE_NAME;


@CrossOrigin
@RestController
@RequestMapping(ORDER_ROUTE_NAME)
@Slf4j
public class OrderRestControllerImp implements OrderRestController {
    @Autowired
    protected OrderService orderService;

    @Override
    public ResponseEntity<byte[]> downloadInvoice(Long id) {
        String attachment =
                "attachment; filename=" + "Label_" + "test " + ".pdf";
        // response.getHeaders().add("Content-Disposition", attachment);
        return ResponseEntity.ok().header("Content-Disposition", attachment).body(orderService.downloadInvoice(id));


    }
}

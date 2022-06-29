package com.hervmo.manager.restcontrollers;

import com.hervmo.manager.models.entities.Customer;
import com.hervmo.manager.models.entities.Role;
import com.hervmo.manager.models.entities.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static com.hervmo.manager.utils.Constants.ROLE_ROUTE_NAME;
import static com.hervmo.manager.utils.Constants.USERS_ROUTE_NAME;

@CrossOrigin
@RequestMapping(USERS_ROUTE_NAME)
public interface UsersController {

    @GetMapping("/{id}")
    ResponseEntity getUser(@PathVariable() Long id);

    @PostMapping("/")
    ResponseEntity<Users> addOrEditUser(@RequestBody Users user);


    @GetMapping("/" + ROLE_ROUTE_NAME + "/")
    ResponseEntity<List<Role>> roleList();

    @PostMapping("/" + ROLE_ROUTE_NAME + "/")
    ResponseEntity<Role> addOrEditRole(@RequestBody Role role);

   /*
   @PutMapping("/edit")
   public ResponseEntity<Users> editUser(@RequestBody Users users);*/


}

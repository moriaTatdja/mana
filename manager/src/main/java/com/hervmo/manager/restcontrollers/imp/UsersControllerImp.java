package com.hervmo.manager.restcontrollers.imp;

import com.hervmo.manager.models.entities.Role;
import com.hervmo.manager.models.entities.Users;
import com.hervmo.manager.restcontrollers.UsersController;
import com.hervmo.manager.services.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class UsersControllerImp implements UsersController {


    @Autowired
    private UsersService usersService;

    @Override
    public ResponseEntity getUser(Long id) {
        try {

            List<Users> listOfUsers = new ArrayList<>();
            if (id == null || id == 0) {
                id = 0L;

                listOfUsers = usersService.getUsers();
            } else {
                listOfUsers.add(usersService.getUser(id, null));
            }
            return ResponseEntity.ok(listOfUsers);
        } catch (Exception e) {
            log.error("getuser with user: " + id + " -> " + e.getMessage(), e);
            return ResponseEntity.internalServerError().body(e);
        }
    }

    @Override
    public ResponseEntity addOrEditUser(Users user) {
        try {
            return ResponseEntity.ok(usersService.saveUser(user, null));
        } catch (Exception e) {
            log.error("addOrEditUser -> " + e.getMessage(), user, e);
            return ResponseEntity.internalServerError().body(e);
        }
    }

    @Override
    public ResponseEntity roleList() {
        try {
            return ResponseEntity.ok().body(usersService.getRoles());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    @Override
    public ResponseEntity addOrEditRole(Role role) {
        try {
            return ResponseEntity.ok(usersService.saveRole(role));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }

    }

}

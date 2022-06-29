package com.hervmo.manager.restcontrollers;

import com.hervmo.manager.models.entities.models.UsersModel;
import com.hervmo.manager.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.hervmo.manager.utils.Constants.REGISTRATION_ROUTE_NAME;

@CrossOrigin
@RequestMapping(REGISTRATION_ROUTE_NAME)
public interface RegistrationController {


    @PostMapping("/")
    ResponseEntity<String> registerUser(@RequestBody UsersModel usersModel);

    @GetMapping("/" + Constants.VERIFY_REGISTRATION_ROUTE_NAME)
    ResponseEntity<String> verifyRegistration(@RequestParam(Constants.TOKEN_REQUEST_PARAMETER) String token);
}

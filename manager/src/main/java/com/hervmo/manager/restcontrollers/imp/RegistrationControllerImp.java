package com.hervmo.manager.restcontrollers.imp;

import com.hervmo.manager.events.registration.RegistrationCompleteEvent;
import com.hervmo.manager.events.registration.VerifyRegistrationErrorEvent;
import com.hervmo.manager.events.registration.VerifyRegistrationSuccessEvent;
import com.hervmo.manager.exception.UserValidationException;
import com.hervmo.manager.models.entities.Users;
import com.hervmo.manager.models.entities.models.UsersModel;
import com.hervmo.manager.restcontrollers.RegistrationController;
import com.hervmo.manager.services.UsersService;
import com.hervmo.manager.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.hervmo.manager.utils.Constants.REGISTRATION_ROUTE_NAME;


@RestController
public class RegistrationControllerImp implements RegistrationController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private Environment env;
    @Autowired
    private ApplicationEventPublisher publisher;

    public ResponseEntity<String> registerUser(UsersModel userModel) {
        try {
            Users user = usersService.registerUser(userModel).orElse(new Users());
            publisher.publishEvent(new RegistrationCompleteEvent(user, env.getProperty("frontend.host")));
            return ResponseEntity.ok(addUserReponse(user));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    public ResponseEntity<String> verifyRegistration(String token) {

        try {
            usersService.validateVerificationToken(token);
            String message = "Youpi Youpi you are a active User now!";

            publisher.publishEvent(new VerifyRegistrationSuccessEvent(message));

            return ResponseEntity.ok(message);
        } catch (UserValidationException e) {
            publisher.publishEvent(new VerifyRegistrationErrorEvent(e.getMessage()));
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    private String addUserReponse(Users user) {
        return "User : " + user.getFirstname() + " " + user.getLastname() + " "
                + "is successful added but please click the  link in your email : "
                + user.getEmail() + " to activate the User";
    }
}

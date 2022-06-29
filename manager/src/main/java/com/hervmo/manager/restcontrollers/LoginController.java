package com.hervmo.manager.restcontrollers;

import com.hervmo.manager.models.entities.models.JwtRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

import static com.hervmo.manager.utils.Constants.LOGIN_ROUTE_NAME;

@CrossOrigin
@RequestMapping(LOGIN_ROUTE_NAME)
public interface LoginController {
    @GetMapping("/refresh")
    ResponseEntity<HashMap<String, String>> refreshToken(HttpServletRequest req, HttpServletResponse res);

    @PostMapping("/")
    ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest, HttpServletRequest req);
}

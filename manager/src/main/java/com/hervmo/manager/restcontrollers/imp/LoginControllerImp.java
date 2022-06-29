package com.hervmo.manager.restcontrollers.imp;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.hervmo.manager.models.entities.Role;
import com.hervmo.manager.models.entities.Users;
import com.hervmo.manager.models.entities.models.JwtRequest;
import com.hervmo.manager.restcontrollers.LoginController;
import com.hervmo.manager.services.UserValidation;
import com.hervmo.manager.services.UsersService;
import com.hervmo.manager.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.stream.Collectors;

import static com.hervmo.manager.utils.Constants.AUTHORIZATION_BEARER_START;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
public class LoginControllerImp implements LoginController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<HashMap<String, String>> refreshToken(HttpServletRequest req, HttpServletResponse res) {
        String authorizationHeader = req.getHeader(AUTHORIZATION);
        HashMap<String, String> rep = new HashMap<>();
        try {
            if (authorizationHeader != null && authorizationHeader.startsWith(AUTHORIZATION_BEARER_START)) {
                DecodedJWT decodedJWT = usersService.getUserValidation().getToken(authorizationHeader);
                String username = decodedJWT.getSubject();
                if (decodedJWT.getExpiresAt().getTime() > System.currentTimeMillis()) {
                    Users user = usersService.getUser(0, username);
                    if (user != null) {
                        UserDetails u = usersService.loadUserByUsername(user.getEmail());
                        return ResponseEntity.ok(usersService.getUserValidation().token(req.getRequestURL().toString(), (User) u));
                    }
                }

                throw new Exception("Refresh Token expired");
            }

            throw new Exception("Authorization no found");

        } catch (Exception e) {
            rep = new HashMap<>();
            rep.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(rep);
        }

    }

    @Override
    public ResponseEntity<?> createAuthenticationToken(JwtRequest authenticationRequest, HttpServletRequest req) {

        try {
            if (authenticationRequest.getUsername() == null || authenticationRequest.getPassword() == null) {
                throw new NullPointerException(
                        (authenticationRequest.getUsername() == null ? "Email" : "password") + " is  null"
                );
            }
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            final UserDetails userDetails = usersService.loadUserByUsername(authenticationRequest.getUsername());
            return ResponseEntity.ok(usersService.getUserValidation().token(req.getRequestURL().toString(), (User) userDetails));
        } catch (Exception e) {
            HashMap<String, String> rep = new HashMap<>();
            rep.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(rep);
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

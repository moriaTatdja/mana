package com.hervmo.manager.services;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hervmo.manager.models.entities.Users;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public interface UserValidation {

    boolean emailValidation(String email);

    boolean requiredField(String field);

    boolean passwordPattern(String password);

    String passwordPatternValue();

    String generateJwt(String username, List<String> roles, String url, long milliSecond, boolean access);

    Algorithm getJwtAlgorithm();

    void updateResponse(String access, String refresh, HttpServletResponse response) throws IOException;

    HashMap<String, String> token(String url, User user);

    void updateResponse(String body, HttpServletResponse response) throws IOException;

    DecodedJWT getToken(String token);

    HashMap<String, String> getStringStringMap(String access, String refresh);
}

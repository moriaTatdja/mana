package com.hervmo.manager.services.imp;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hervmo.manager.services.UserValidation;
import com.hervmo.manager.utils.Constants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.hervmo.manager.utils.Constants.AUTHORIZATION_BEARER_START;
import static com.hervmo.manager.utils.Constants.JWT_CLAIMS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
public class UserValidationImp implements UserValidation {

    @Override
    public boolean emailValidation(String email) {
        Optional<String> emailOptional = Optional.ofNullable(email);
        return !(emailOptional.isPresent() && emailOptional.get().contains("@") && emailOptional.get().contains("."));

    }

    @Override
    public boolean requiredField(String field) {
        Optional<String> requiredField = Optional.ofNullable(field);
        return requiredField.isEmpty();
    }

    @Override
    public boolean passwordPattern(String password) {
        Optional<String> pass = Optional.ofNullable(password);
        //  one digit [0-9] must be included in the password.
        //  one lowercase character [a-z] is required in the password.
        //  one uppercase character [A-Z] is required in the password.
        //  one special character, such as ! @ # & (), must be included in the password.
        //  password must be at least 8 characters long and no more than 60 characters long.
        String regexPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,60}$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher passwordMacther = pattern.matcher(pass.orElse(""));
        return !(pass.isPresent() && passwordMacther.matches());
    }

    public String passwordPatternValue() {
        return "" +
                " one digit [0-9] must be included in the password." +
                " and  one lowercase character [a-z] is required in the password." +
                " and  one uppercase character [A-Z] is required in the password." +
                " and  one special character, such as ! @ # & (), must be included in the password." +
                " and password must be at least 8 characters long and no more than 60 characters long.";
    }

    @Override
    public String generateJwt(String username, List<String> roles, String url, long milliSecond, boolean access) {
        Date d = new Date(milliSecond);
        return access ? JWT.create()
                .withSubject(username)
                .withExpiresAt(d)
                .withIssuer(url)
                .withClaim(JWT_CLAIMS, roles)
                .sign(getJwtAlgorithm()) : JWT.create()
                .withSubject(username)
                .withExpiresAt(d)
                .withIssuer(url)
                .sign(getJwtAlgorithm());
    }

    @Override
    public Algorithm getJwtAlgorithm() {
        return Algorithm.HMAC256((Constants.ALGORITHM_SECRET).getBytes());
    }

    @Override
    public void updateResponse(String access, String refresh, HttpServletResponse response) throws IOException {
        Map<String, String> token = getStringStringMap(access, refresh);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), token);
    }

    public HashMap<String, String> getStringStringMap(String access, String refresh) {
        HashMap<String, String> token = new HashMap<>();
        token.put("access_token", access);
        token.put("refresh_token", refresh);
        token.forEach((x, a) -> System.out.println(a));
        return token;
    }

    @Override
    public HashMap<String, String> token(String url, User user) {
        long unixtime = System.currentTimeMillis();
        long accessUnixtime = (unixtime + (Constants.ACCESS_TOKEN_DURATION));
        long refreshUnixtime = (unixtime + (Constants.REFRESH_TOKEN_DURATION * Constants.ACCESS_TOKEN_DURATION));
        return Constants.GENERATED_JWT(generateJwt(user.getUsername(),
                        user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()),
                        url, accessUnixtime, true),
                generateJwt(user.getUsername(),
                        user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()),
                        url, refreshUnixtime, false), accessUnixtime, refreshUnixtime);


    }

    @Override
    public void updateResponse(String body, HttpServletResponse response) throws IOException {
        Map<String, String> token = new HashMap<>();
        token.put("message", body);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), token);
    }

    @Override
    public DecodedJWT getToken(String token) throws TokenExpiredException {
        String tokenWithoutBearer = token.substring(AUTHORIZATION_BEARER_START.length());
        JWTVerifier verifier = JWT.require(getJwtAlgorithm()).build();
        return verifier.verify(tokenWithoutBearer);

    }
}

package com.hervmo.manager.config.filter;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hervmo.manager.services.UserValidation;
import com.hervmo.manager.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.hervmo.manager.utils.Constants.JWT_CLAIMS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final UserValidation userValidation;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, UserValidation userValidation) {
        this.authenticationManager = authenticationManager;
        this.userValidation = userValidation;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (CorsUtils.isPreFlightRequest(request)) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        String username = request.getParameter("username");
        String pass = request.getParameter("password");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, pass);
        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        try {
            User user = (User) authResult.getPrincipal();
            if (user != null) {
                HashMap<String, String> token = userValidation.token(request.getRequestURL().toString(), user);

                response.setContentType(APPLICATION_JSON_VALUE);
                ObjectMapper o = new ObjectMapper();
                o.writeValue(response.getOutputStream(), token);
            }
        } catch (Exception e) {
            log.error("error  ", e);

        }


    }

}

package com.hervmo.manager.config.filter;


import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hervmo.manager.models.entities.Users;
import com.hervmo.manager.services.UserValidation;
import com.hervmo.manager.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.hervmo.manager.utils.Constants.*;
import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private final UsersService usersService;

    public CustomAuthorizationFilter(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("here");
        if (request.getServletPath().contains(LOGIN_ROUTE_NAME)) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            System.out.println(authorizationHeader);
            if (authorizationHeader != null && authorizationHeader.startsWith(AUTHORIZATION_BEARER_START)) {
                try {
                    DecodedJWT decodedJWT = usersService.getUserValidation().getToken(authorizationHeader);
                    String username = decodedJWT.getSubject();
                    Users user = usersService.getUser(0, username);
                    System.out.println(username);

                    if (user == null) {
                        throw new UsernameNotFoundException("user not found");
                    }

                    String[] roles = decodedJWT.getClaim(JWT_CLAIMS).asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    authenticationToken.setDetails(new
                            WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    filterChain.doFilter(request, response);
                } catch (UsernameNotFoundException | JWTDecodeException | TokenExpiredException e) {

                    response.setStatus(FORBIDDEN.value());
                    Map<String, String> token = new HashMap<>();
                    token.put("message", e.getMessage());
                    token.put("tokenExpired", true + "");
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), token);
                } catch (IOException | ServletException e) {
                    throw new RuntimeException(e);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }

    }
}

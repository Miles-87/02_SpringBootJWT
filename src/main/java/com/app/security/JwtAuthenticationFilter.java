package com.app.security;

import com.app.exceptions.ExceptionCode;
import com.app.exceptions.MyException;
import com.app.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

// ten filter bedzie wywolywany po kazdej probie logowania
// bedzie przechwytywal username, password i na ich podstawie logowal usera a potem
// generowal dla niego token
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager; // obiekt ktory pomoze zalogowac
    // usera

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // metoda wywola sie kiedy bedie proba logowania
    // zwracany obiket Authentication to obiekt ktory reprezentuje zalogowanego usera
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // pobieramy usera przeslanego JSON-em w ramach REST
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword(),
                    Collections.emptyList()
            ));
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SECURITY, e.getMessage());
        }
    }

    // metoda wywola sie kiedy logowanie zakonczy sie sukcesem
    // metoda przechwytuje obiket Authentication jako ostatni argument i ten obiekt
    // to ten sam ktory wygenerowala metoda attempAuthentication
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        /*String token = Jwts.builder()
                .setSubject(((org.springframework.security.core.userdetails.User)authResult.getPrincipal()).getUsername())
                .setExpiration()
                .compact();*/
    }
}

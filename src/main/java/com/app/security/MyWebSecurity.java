package com.app.security;

import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class MyWebSecurity extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;
    private UserRepository userRepository;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    public MyWebSecurity(
            @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
            UserRepository userRepository,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // https://pl.wikipedia.org/wiki/Cross-site_request_forgery
                // https://www.baeldung.com/spring-security-csrf
                // majac jwt nie potrzebujemy csrf wiec go wylaczamy
                .csrf().disable()

                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)

                .and()
                .authorizeRequests()
                .antMatchers("/testUser").hasRole("USER")
                .antMatchers("/testAdmin").hasRole("ADMIN")
                .anyRequest().authenticated()

                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                // .and()
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

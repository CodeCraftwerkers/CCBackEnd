package com.codecrafters.ccbackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.codecrafters.ccbackend.security.filter.JWTAuthentication;
import com.codecrafters.ccbackend.security.filter.JWTAuthorization;

@Configuration
public class SpringConfig {

    private CustomAuthenticationManager customAuthenticationManager;

    public SpringConfig(CustomAuthenticationManager customAuthenticationManager) {
        this.customAuthenticationManager = customAuthenticationManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        JWTAuthentication jwtAuthentication = new JWTAuthentication(customAuthenticationManager);
        jwtAuthentication.setFilterProcessesUrl("/login");

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.GET, "/api/v1/events", "/api/v1/events/filter",
                                "/api/v1/events/**")
                        .permitAll()
                       .requestMatchers(HttpMethod.GET, "/api/v1/events/signup/**").hasRole("USER")
                       .requestMatchers(HttpMethod.DELETE, "/api/v1/events/**").authenticated()
                        .anyRequest().authenticated())
                .addFilter(jwtAuthentication)
                .addFilterAfter(new JWTAuthorization(), JWTAuthentication.class)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

}

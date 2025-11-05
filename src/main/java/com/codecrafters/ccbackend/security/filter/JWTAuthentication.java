package com.codecrafters.ccbackend.security.filter;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.codecrafters.ccbackend.entity.User;
import com.codecrafters.ccbackend.security.CustomAuthenticationManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthentication extends UsernamePasswordAuthenticationFilter {

    private CustomAuthenticationManager customAuthenticationManager;

    public JWTAuthentication(CustomAuthenticationManager customAuthenticationManager) {
        this.customAuthenticationManager = customAuthenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            
    throws AuthenticationException {
        
        try {
            //Log 1: Indica que o filtro JWTAuthentication foi chamado
        System.out.println(" [JWTAuthentication] Tentando autenticar login...");

        //Log 2: Mostra o corpo bruto da requisição (JSON enviado pelo front)
        String body = request.getReader().lines().reduce("", (acc, line) -> acc + line);
        System.out.println(" [JWTAuthentication] Corpo recebido: " + body);

        // Converte o JSON em um objeto User
        User user = new ObjectMapper().readValue(body, User.class);

        //Log 3: Mostra o email e senha recebidos
        System.out.println(" [JWTAuthentication] Email: " + user.getEmail());
        System.out.println(" [JWTAuthentication] Senha: " + user.getPassword());
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),
                    user.getPassword());
            return customAuthenticationManager.authenticate(authentication);
        } catch (Exception e) {
            System.err.println("Erro durante autenticação: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        e.printStackTrace(); // mostra onde ocorreu
        throw new RuntimeException("Erro ao tentar autenticar usuário: " + e.getMessage(), e);
        }
    }

    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(failed.getMessage());
        response.getWriter().flush();
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) {
        List<String> roles = authResult.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority().replace("ROLE_ ", ""))
                .collect(Collectors.toList());

        String token = JWT.create()
                .withSubject(authResult.getName())
                .withClaim("roles", roles)
                .withExpiresAt(new Date(System.currentTimeMillis() + (5 * 60000)))
                .sign(Algorithm.HMAC512("383jdshjd8ADADF$$fsdfs4d.r4fse4frfr"));
        response.addHeader("Authorization", "Bearer " + token);
    }

}

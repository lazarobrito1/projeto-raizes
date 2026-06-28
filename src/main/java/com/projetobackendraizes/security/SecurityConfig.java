package com.projetobackendraizes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity

/*
   Classe responsável pela configuração de segurança da aplicação.
   Define como o Spring Security vai controlar o acesso às rotas,
   permitindo ou bloqueando requisições e servindo como base
   para futuras regras de autenticação e autorização.
*/
public class SecurityConfig {

    /* Instancia do filtro */
    @Autowired
    private AutenticadorJWTFiltro filtroJWT;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                /*
                   Desabilita o CSRF (ESTA É UMA PROTEÇÃO
                   QUE O SPRING INTRODUZ PARA BLOQUEAR
                   ESSE TIPO DE ATAQUE) para permitir requisições
                   POST do Postman
                */
                .csrf(csrf -> csrf.disable())

                // Define a política de sessão como STATELESS (não guarda nada).
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        // Libera explicitamente as rotas públicas (ex: login e cadastro)
                        .requestMatchers("/auth/login", "/cliente/cadastrar", "/error").permitAll()
                        // Exige autenticação (token) para QUALQUER outra rota do sistema
                        .anyRequest().authenticated()

                )
                .addFilterBefore(filtroJWT, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}


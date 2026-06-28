package com.projetobackendraizes.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration

/*
   Classe de configuração responsável por disponibilizar o encoder de senha da aplicação.
   Aqui é criado um Bean do BCryptPasswordEncoder, que será usado para
   criptografar (hash) senhas antes de salvar no banco e também para validar
   senhas durante o login.
   Em resumo, centraliza o mecanismo de segurança relacionado a senhas.
*/
public class HashConfig {
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}

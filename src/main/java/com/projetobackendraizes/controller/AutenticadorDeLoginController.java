package com.projetobackendraizes.controller;

import com.projetobackendraizes.dto.DadosParaAutenticarDTO;
import com.projetobackendraizes.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
/* Definição da rota base */
@RequestMapping("/auth")
public class AutenticadorDeLoginController {
    private AuthService authService;

    public AutenticadorDeLoginController(AuthService authService) {
        this.authService = authService;
    }

    /*
      Rota do tipo POST para recebimento
      das informações de autenticação e
      designação do caminho para chamada
      do método responsável por repassar
      os dados para a AuthService.
    */
    @PostMapping("/login")
    /*
      "DadosParaAutenticarDTO" atua como tipo de dado,
       pois caracteriza um objeto Java
       que representa as credencias de acesso.
    */
    public ResponseEntity<String> entrar(@RequestBody @Valid DadosParaAutenticarDTO dadosLogin) {


        return ResponseEntity.ok("Login realizado com sucesso! Token gerado:" + authService.autenticarCliente(dadosLogin));
    }

}

package com.projetobackendraizes.controller;

import com.projetobackendraizes.dto.ProdutoRequestDTO;
import com.projetobackendraizes.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/consultar")

     /*
           O parâmetro Principal é injetado automaticamente pelo Spring.
           COMO O FILTRO JWT JÁ FOI EXECUTADO ANTES, validou o token
           e salvou o usuário no SecurityContextHolder, o Spring consegue
           recuperar o login desse cliente direto aqui.
           Dessa forma, não precisamos ler o cabeçalho Authorization
           manualmente dentro do Controller.
      */

    public ResponseEntity<List<ProdutoRequestDTO>> consultarProdutos(@RequestParam Long unidadeId, Principal principal) {
        List<ProdutoRequestDTO> produtos = produtoService.consultarProdutos(unidadeId);
        return ResponseEntity.ok(produtos);
    }
}

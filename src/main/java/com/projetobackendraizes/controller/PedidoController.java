package com.projetobackendraizes.controller;


import com.projetobackendraizes.dto.DadosDoPedidoDTO;
import com.projetobackendraizes.entity.Pedido;
import com.projetobackendraizes.entity.Produto;
import com.projetobackendraizes.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/realizar")
    /*
      Criei um objeto DTO com representação dos dados do pedido,
      para passar quantidade, id do produto e canal do pedido
      ao processamento do pedido pela service. A "principal"
      é a interface do spring security que representa a
      identidade do usuario que ja autenticado no sistema.
      Então esta classe recebe os dados do pedido e o usuario (autenticado)
      que fez o pedido após a busca no catálago .
    */
    public ResponseEntity<Pedido> realizarPedido(@RequestBody DadosDoPedidoDTO dadosDoPedidoDTO, Principal principal) {
        /* Captura o email do cliente autenticado no JWT */
        String loginCliente = principal.getName();

        /* Passa o produto e o email do cliente para o Service */
        Pedido pedido = pedidoService.realizarPedido(dadosDoPedidoDTO, loginCliente);

        return ResponseEntity.ok(pedido);
    }
}


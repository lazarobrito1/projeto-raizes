package com.projetobackendraizes.controller;

import com.projetobackendraizes.dto.PagamentoDTO;
import com.projetobackendraizes.service.PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    /*
       A notação @PathVariable vincula o {idPedido} inserido na URL diretamente
       ao parâmetro do método, permitindo identificar qual compra será feita a operação de pagamento.
    */
    @PostMapping("/{idPedido}/pagar")
    public ResponseEntity<PagamentoDTO> pagarPedido(@PathVariable Long idPedido) {

        /*  Repassa o ID para a camada de serviço executar a simulação do mock */
        PagamentoDTO resultado = pagamentoService.PagamentoSimulado(idPedido);

        return ResponseEntity.ok(resultado);
    }
}
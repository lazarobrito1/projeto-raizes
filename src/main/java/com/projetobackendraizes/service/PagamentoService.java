package com.projetobackendraizes.service;

import com.projetobackendraizes.dto.PagamentoDTO;
import com.projetobackendraizes.entity.Pagamento;
import com.projetobackendraizes.entity.Pedido;
import com.projetobackendraizes.exceptions.ExcecaoNegocio;
import com.projetobackendraizes.exceptions.RecursoAusente;
import com.projetobackendraizes.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PagamentoService {
    private final PedidoRepository pedidoRepository;

    public PagamentoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }


    @Transactional
    /*
       Método responsável por simular a aprovação financeira do pedido.
       Modifica o estado do pedido no banco de dados após a validação das regras.
    */
    public PagamentoDTO PagamentoSimulado(Long idPedido) {

        /* Busca o pedido no banco de dados para verificar se ele realmente existe */
        Pedido pedidoDoBD = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RecursoAusente("Pedido não encontrado"));

        /*
        Compara o status do pedido que está no banco para
        validar se ele realmente está aguardando pg.
         */
        if (!"Aguardando_pagamento".equals(pedidoDoBD.getStatus())) {
            throw new ExcecaoNegocio("O pedido não está pendente de pagamento. Status: " + pedidoDoBD.getStatus());
        }

        /*
           Simulaçao de pagamento aprovado no cartão.
        */
        boolean transacaoAprovada = true;

        if (transacaoAprovada) {

            /*
               O status do pedido deve ser alterado conforme a conclusão.
            */
            pedidoDoBD.setStatus("PAGO");
            pedidoRepository.save(pedidoDoBD);

            return new PagamentoDTO(
                    idPedido,
                    "APROVADO",
                    "Pagamento aprovado. Seu pedido está sendo preparado"
            );

        } else {
            /* Caso a simulação falhe, gera este erro alternativo */
            pedidoDoBD.setStatus("CANCELADO");
            pedidoRepository.save(pedidoDoBD);

            throw new ExcecaoNegocio("Erro na transação de pagamento");
        }
    }
}
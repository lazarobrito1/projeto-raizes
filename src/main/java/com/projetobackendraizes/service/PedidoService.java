package com.projetobackendraizes.service;


import com.projetobackendraizes.dto.DadosDoPedidoDTO;
import com.projetobackendraizes.entity.*;
import com.projetobackendraizes.exceptions.ExcecaoNegocio;
import com.projetobackendraizes.exceptions.RecursoAusente;
import com.projetobackendraizes.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PedidoService {
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;
    private final UnidadeRaizesRepository unidadeRepository; // Injetado para validar a lanchonete
    private final EstoqueUnidadeRepository estoqueRepository; // Injetado para dar baixa na 3FN
    private final PedidoRepository pedidoRepository; // Para persistir o pedido criado

    public PedidoService(ProdutoRepository produtoRepository, ClienteRepository clienteRepository,
                         UnidadeRaizesRepository unidadeRepository, EstoqueUnidadeRepository estoqueRepository,
                         PedidoRepository pedidoRepository) {
        this.produtoRepository = produtoRepository;
        this.clienteRepository = clienteRepository;
        this.unidadeRepository = unidadeRepository;
        this.estoqueRepository = estoqueRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    public Pedido realizarPedido(DadosDoPedidoDTO produtoDados, String login) {

        /* Valida se o cliente existe */

        Cliente clienteEncontrado = clienteRepository.findBylogin(login);
        if (clienteEncontrado == null) {
            throw new ExcecaoNegocio("Cliente não encontrado com o login: " + login);
        }

        /*  Valida se a unidade física informada existe */
        UnidadeRaizes unidadeEncontrada = unidadeRepository.findById(produtoDados.getIdUnidade())
                .orElseThrow(() -> new RecursoAusente("Unidade não encontrada."));

        /* Valida se o produto existe no contexto geral */
        Produto produtoEncontrado = produtoRepository.findById(produtoDados.getIdProduto())
                .orElseThrow(() -> new RecursoAusente("Produto não encontrado."));

        /*
           Busca o estoque específico desse
           produto nesta unidade
           Caso não encontre o registro,
           significa que este
           item nunca foi abastecido nesta filial.
        */
        ProdutoUnidade estoqueLocal = estoqueRepository
                .findByUnidadeRaizes_IdUnidadeAndProdutoIdProduto
                        (unidadeEncontrada.getIdUnidade(), produtoEncontrado.getIdProduto())
                .orElseThrow(() -> new ExcecaoNegocio("Estoque insuficiente"));

        /* Validação de quantidade */
        if (produtoDados.getQuantidade() < 1) {
            throw new ExcecaoNegocio("A quantidade do pedido deve ser de no mínimo 1 item.");
        }

        /*
           Verifica se há saldo suficiente.
        */
        if (estoqueLocal.getQuantidade() < produtoDados.getQuantidade()) {
            throw new ExcecaoNegocio("Estoque insuficiente");
        }

        /* Calcula o valor total multiplicando o preço da tabela geral pela quantidade informada */
        BigDecimal valorTotal = produtoEncontrado.getPreco()
                .multiply(BigDecimal.valueOf(produtoDados.getQuantidade()));


        Pedido pedido = new Pedido();
        pedido.setCliente(clienteEncontrado);
        pedido.setUnidade(unidadeEncontrada);
        pedido.setCanalPedido(produtoDados.getCanalPedido());
        pedido.setStatus("Aguardando_pagamento");
        pedido.setValorTotal(valorTotal);

        /* Redução da quantidade na unidade de lanchonete correspondente*/
        estoqueLocal.setQuantidade(estoqueLocal.getQuantidade() - produtoDados.getQuantidade());
        estoqueRepository.save(estoqueLocal);

        /* Retorna o pedido para o controler */
        return pedidoRepository.save(pedido);
    }
}

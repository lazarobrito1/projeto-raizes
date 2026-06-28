package com.projetobackendraizes.service;

import com.projetobackendraizes.dto.ProdutoRequestDTO;
import com.projetobackendraizes.entity.ProdutoUnidade;
import com.projetobackendraizes.exceptions.RecursoAusente;
import com.projetobackendraizes.repository.EstoqueUnidadeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoService {

    private final EstoqueUnidadeRepository estoqueRepository;

    public ProdutoService(EstoqueUnidadeRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    /*
       Método responsável por varrer o repositório de estoques.
       A busca é filtrada estritamente pelo ID da unidade, que vem ja no URL, garantindo
       que o cliente só veja a quantidade disponível na loja onde ele está comprando.
    */
    public List<ProdutoRequestDTO> consultarProdutos(Long unidadeId) {

        /*
          Recupera do banco todos os
           vínculos de produtos pertencentes
           àquela lanchonete
        */
        List<ProdutoUnidade> estoquesDaLoja = estoqueRepository.findByUnidadeRaizes_IdUnidade(unidadeId);

        /*
           Se a lista retornada estiver vazia, significa que a unidade informada
           não possui nenhum produto cadastrado ou ativo no sistema.
        */
        if (estoquesDaLoja.isEmpty()) {
            throw new RecursoAusente("Nenhum produto cadastrado para esta lanchonete.");
        }

        /*
           Lista auxiliar criada manualmente para
           transferir os dados das entidades para os
           DTOs seguros
        */
        List<ProdutoRequestDTO> cardapioFormatado = new ArrayList<>();

        /*
           Laço de repetição (for-each) que percorre cada item do estoque da lanchonete.
           Extraímos o produto e sua respectiva quantidade local, isolando as informações
           de banco de dados da camada de exibição da API .
        */
        for (ProdutoUnidade item : estoquesDaLoja) {
            ProdutoRequestDTO linhaDoCardapio = new ProdutoRequestDTO(
                    item.getProduto().getIdProduto(),
                    item.getProduto().getNome(),
                    item.getProduto().getPreco(),
                    item.getQuantidade()
            );

            cardapioFormatado.add(linhaDoCardapio);
        }

        return cardapioFormatado;
    }
}



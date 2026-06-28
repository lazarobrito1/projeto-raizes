package com.projetobackendraizes.repository;

import com.projetobackendraizes.entity.ProdutoUnidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/* ProdutoUnidade é a tabela que irá guardar os dados de produtos por unidade */
public interface EstoqueUnidadeRepository extends JpaRepository<ProdutoUnidade, Long> {

    /*
       como temos a tabela intermediaria ProdutoUnidade, podemos
       buscar os produtos por unidade do estabelecimento.
    */
    List<ProdutoUnidade> findByUnidadeRaizes_IdUnidade(Long idUnidade);

    /* Método extra necessário para encontrar o estoque de um produto específico dentro de uma unidade */
    Optional<ProdutoUnidade> findByUnidadeRaizes_IdUnidadeAndProdutoIdProduto(Long idUnidade, Long idProduto);
}

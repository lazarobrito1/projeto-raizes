package com.projetobackendraizes.repository;

import com.projetobackendraizes.entity.PedidoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoProdutoRepository extends JpaRepository<PedidoProduto, Long> {
    List<PedidoProduto> findByProdutoIdProduto(
            Long idProduto
    );

    List<PedidoProduto> findByPedidoIdPedido(Long idPedido);

    List<PedidoProduto> findByPedidoIdPedidoAndProdutoIdProduto(
            Long idPedido,
            Long idProduto
    );
}

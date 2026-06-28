package com.projetobackendraizes.repository;

import com.projetobackendraizes.entity.Cliente;
import com.projetobackendraizes.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}

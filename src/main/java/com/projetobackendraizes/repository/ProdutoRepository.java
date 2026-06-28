package com.projetobackendraizes.repository;

import com.projetobackendraizes.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {


    List<Produto> findByNome(String nome);


    List<Produto> findByPrecoLessThan(BigDecimal preco);

}

package com.projetobackendraizes.repository;

import com.projetobackendraizes.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    /* Definição do critério de busca para que o Spring possa gerar consultas. */
    Cliente findBylogin(String login);

}
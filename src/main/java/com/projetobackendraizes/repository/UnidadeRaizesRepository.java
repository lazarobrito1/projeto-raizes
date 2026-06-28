package com.projetobackendraizes.repository;

import com.projetobackendraizes.entity.UnidadeRaizes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeRaizesRepository extends JpaRepository<UnidadeRaizes, Long> {

}


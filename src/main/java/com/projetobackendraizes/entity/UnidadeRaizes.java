package com.projetobackendraizes.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "unidade")
public class UnidadeRaizes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUnidade;
    private String nomeDaUnidade;
}

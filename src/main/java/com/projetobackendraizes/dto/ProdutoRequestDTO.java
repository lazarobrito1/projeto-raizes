package com.projetobackendraizes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
public class ProdutoRequestDTO {
    private Long idProduto;
    private String nome;
    private BigDecimal preco;
    private int quantidadeDisponivel;
}

package com.projetobackendraizes.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Getter
@Table(name = "produto")
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduto;


    private String nome;
    private BigDecimal preco;

    /*
    A quantidade foi vinculada diretamente ao produto,
   pois isso simplifica a lógica de fechamento de pedidos.

   Dessa forma, o sistema consegue verificar diretamente se o produto
   possui quantidade suficiente para concluir a compra, sem precisar
   consultar um estoque geral por categoria.

   O estoque passa a funcionar apenas como uma forma de categorização
   e organização dos produtos dentro de um sistema, futuramente, pelo lado administrativo.
    */

}

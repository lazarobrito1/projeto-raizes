package com.projetobackendraizes.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "estoque_unidade")

/*
Como precisamos separar a quantidade de produtos por unidade
e pensando nas 3FN's, foi criada esta classe para representar
os produtos e suas quantidades por unidade da rede, resolvendo
o problema de muitos para muitos.
*/
public class ProdutoUnidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idUnidade")
    private UnidadeRaizes unidadeRaizes;

    @ManyToOne
    @JoinColumn(name = "idProduto")
    private Produto produto;

    private int quantidade;

}

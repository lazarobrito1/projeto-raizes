package com.projetobackendraizes.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@Table(name = "pedidoProduto")
public class PedidoProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedidoProduto;

    private int quantidade;
    private BigDecimal precoUnitario;


    /*
   Em relacionamentos muitos para muitos, é necessária a utilização
   de uma entidade intermediária para organizar a relação entre as
   múltiplas entidades envolvidas.

   Nesse caso, a entidade funciona como uma tabela associativa entre
   Pedido e Produto, armazenando as chaves estrangeiras de ambas
   as entidades para representar corretamente a relação.

   Essa abordagem segue os princípios de normalização de bancos de dados,
   evitando redundância de informações e promovendo maior integridade
   referencial, já que cada associação entre pedido e produto passa
   a possuir um registro próprio dentro do sistema.
    */
    @ManyToOne
    @JoinColumn(name = "idPedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "idProduto")
    private Produto produto;
}

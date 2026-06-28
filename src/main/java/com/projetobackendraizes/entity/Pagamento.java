package com.projetobackendraizes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Table(name = "pagamento")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPagamento;

    private String statusPagamento;
    private BigDecimal valorPg;



    /*
     A relação entre Pedido e Pagamento foi modelada como 1 para
     1, pois cada pedido possui apenas um pagamento associado.
     Nesse caso, a chave estrangeira permanece na entidade Pagamento,
     já que o pagamento depende diretamente da existência do pedido
     para ser processado e registrado no sistema.
    */
    @OneToOne
    @JoinColumn(name = "idPedido")
    private Pedido pedido;
}

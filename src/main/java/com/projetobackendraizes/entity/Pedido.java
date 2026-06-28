package com.projetobackendraizes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "pedido")
@NoArgsConstructor
@AllArgsConstructor

public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) /* Permite que o banco gere os IDs automaticamente*/
    private Long idPedido;

    @ManyToOne /* Muitos pedidos podem pertencer a um cliente */
    @JoinColumn(name = "idCliente") /* Chave estrangeira */
    private Cliente cliente; /* Referência à classe relacionada */

    @ManyToOne
    @JoinColumn(name = "idUnidade") /* Vincula o pedido a lanchonete física correspondente */
    private UnidadeRaizes unidade;

    @Enumerated(EnumType.STRING) /* Por padrão o JPA salva enum com numeros inteiros, então é preciso modificar o tipo para salvar string*/
    private CanalPedidoGroupConstant canalPedido;
    private String status;
    private BigDecimal valorTotal;
}

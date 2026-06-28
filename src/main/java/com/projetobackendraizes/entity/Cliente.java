package com.projetobackendraizes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Table(name = "cliente")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    private String nome;

    private String login;
    @JsonIgnore /* Ao montar o json para retorno dos dados do pedido esta informação fica oculta */
    private String senhaHash;


    @JsonIgnore /* Ao montar o json para retorno dos dados do pedido esta informação fica oculta */
    @OneToMany(mappedBy = "cliente")
    /*
       O mapeamento da coleção de pedidos foi realizado para representar a relação
       de um para muitos entre cliente e pedido, bem como para permitir consultas
       futuras ao histórico de compras associado a cada cliente.
    */
    private List<Pedido> pedidos;
}

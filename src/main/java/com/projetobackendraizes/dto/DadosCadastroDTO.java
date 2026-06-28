package com.projetobackendraizes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/*
  Para não usar e entidade cliente como tipo do parametro
  recebido no controler, e para separar dados que serão
  usados para regras de negocio daquelas apenas para
  representar objetos de tranferencia de informação
  criamos este DTO que caracteriza os dados passados
  pelo cliente ao fazer cadastro.
*/
public class DadosCadastroDTO {
    private String nome;
    private String login;
    private String senha;
}

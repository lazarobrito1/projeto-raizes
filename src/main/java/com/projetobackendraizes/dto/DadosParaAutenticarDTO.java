package com.projetobackendraizes.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


/*
  Classe responsável apenas por converter dados
  de login e senha em objeto Java.
  É feita para não misturar dados reais
  de clientes persistidos.
*/
@Data
public class DadosParaAutenticarDTO {
    /*
      A notação @NotBlank garante que o campo
      não seja nulo, não seja vazio e não contenha
      apenas espaços em branco. é ideal
      para garantir o preechimento dos campos inseridos
      pelo usuários: login e senha.

    */
    @NotBlank
    String login;
    @NotBlank
    String senha;
}

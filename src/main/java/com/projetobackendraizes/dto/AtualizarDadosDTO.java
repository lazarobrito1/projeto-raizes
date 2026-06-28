package com.projetobackendraizes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtualizarDadosDTO {
    private Long id;
    private String nome;
    private String login;
    private String senha;
}

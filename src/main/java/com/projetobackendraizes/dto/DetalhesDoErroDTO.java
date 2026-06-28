package com.projetobackendraizes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
/*
Classe que atua como um DTO usado para padronizar e detalhar mensagens de erro
é usado na classe (respostaErroDTO) mostrado ao cliente
 */
public class DetalhesDoErroDTO {
    private String campoComErro;
    private String motivoErro;
}


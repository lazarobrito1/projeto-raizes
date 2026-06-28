package com.projetobackendraizes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
/*
Estilizador de erro para o cliente. Monta uma mensagem de erro
menos tecnica e melhor visualmente para o cliente.
Esta classe é usada pelo ControlerAdvice.
*/
public class RespostaErroDTO {
    /* Mostra o tipo de erro */

    private String erro;
    /* Explicação mais geral do erro */
    private String messagem;
    /* Guarda detalhes do erro, de forma mais específica*/
    private List<DetalhesDoErroDTO> details;
    /* Registra o momento exato em que o erro aconteceu */
    private Instant timestamp;
    /* Armazena o endereço (URL/Endpoint) que o cliente tentou acessar quando o erro ocorreu */
    private String path;
}


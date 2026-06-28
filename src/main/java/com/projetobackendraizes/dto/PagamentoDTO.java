package com.projetobackendraizes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PagamentoDTO {
    private Long idPedido;
    private String statusPagamento;
    private String mensagem;
}

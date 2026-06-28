package com.projetobackendraizes.dto;

import com.projetobackendraizes.entity.CanalPedidoGroupConstant;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DadosDoPedidoDTO {
    private Long idUnidade;
    private Long idProduto;
    private int quantidade;
    private CanalPedidoGroupConstant canalPedido;

}

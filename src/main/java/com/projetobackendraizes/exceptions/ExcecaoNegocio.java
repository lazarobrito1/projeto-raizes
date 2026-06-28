package com.projetobackendraizes.exceptions;

/*Exceção personalizada para interromper um fluxo de código quando uma regra de negócio for violada*/
public class ExcecaoNegocio extends RuntimeException {
    public ExcecaoNegocio(String msg) {
        super(msg);
    }
}

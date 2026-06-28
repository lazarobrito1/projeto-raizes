package com.projetobackendraizes.exceptions;

/*Exceção personalizada para interromper um fluxo de código quando uma regra de negócio for violada*/
public class RecursoAusente extends RuntimeException {
    public RecursoAusente(String msg) {
        super(msg);
    }
}



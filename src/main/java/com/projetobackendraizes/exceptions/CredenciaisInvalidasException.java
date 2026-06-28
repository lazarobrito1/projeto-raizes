package com.projetobackendraizes.exceptions;

/*
   Classe de exceção que representa falhas na tentativa de login,
   como usuário não encontrado ou senha divergente.
*/
public class CredenciaisInvalidasException extends RuntimeException {
    public CredenciaisInvalidasException(String msg) {
        super(msg);
    }
}

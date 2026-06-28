package com.projetobackendraizes.controller;

import com.projetobackendraizes.dto.RespostaErroDTO;
import com.projetobackendraizes.exceptions.CredenciaisInvalidasException;
import com.projetobackendraizes.exceptions.ExcecaoNegocio;
import com.projetobackendraizes.exceptions.RecursoAusente;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.ArrayList;

@ControllerAdvice
/*
   Classe de configuração responsável por centralizar o tratamento de erros da aplicação.
   Ela captura as exceções lançadas pelo sistema e as transforma em respostas JSON
   padronizadas utilizando o RespostaErroDTO.
   Em resumo, elimina os blocos try-catch dos controllers e garante os status HTTP corretos.
*/
public class TratadorDeErroGlobal {
    /*Trata erros de regras de negócio ou duplicidade  */
    @ExceptionHandler(ExcecaoNegocio.class)
    /* Mapeia o DTO (RespostaErroDTO) que personaliza a mensagem*/
    public ResponseEntity<RespostaErroDTO> conflito(ExcecaoNegocio e, HttpServletRequest request) {
        RespostaErroDTO conflitoRegra = new RespostaErroDTO(
                "ERRO_DE_REGRA_DE_NEGOCIO",
                e.getMessage(),
                new ArrayList<>(),
                Instant.now(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(conflitoRegra);
    }

    /* Trata erros de registros não encontrados  */
    @ExceptionHandler(RecursoAusente.class)
    public ResponseEntity<RespostaErroDTO> naoEncontrado(RecursoAusente e, HttpServletRequest request) {
        RespostaErroDTO ausenciaRecurso = new RespostaErroDTO(
                "RECURSO_NAO_ENCONTRADO",
                e.getMessage(),
                new ArrayList<>(),
                Instant.now(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ausenciaRecurso);
    }

    /* Trata tentativas de login com credenciais incorretas */
    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<RespostaErroDTO> autorizadcaoNaoPermitida (CredenciaisInvalidasException e, HttpServletRequest request) {
        RespostaErroDTO falhaNaAutenticacao = new RespostaErroDTO(
                "CREDENCIAIS_INVALIDAS",
                e.getMessage(),
                new ArrayList<>(),
                Instant.now(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(falhaNaAutenticacao); // Devolve o status 401
    }
}

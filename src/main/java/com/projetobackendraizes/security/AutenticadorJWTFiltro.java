package com.projetobackendraizes.security;


import com.projetobackendraizes.repository.ClienteRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/*
  Quando fizer login,
  vai ser gerado um token, e esse token será
  passado em cabeçalho, via http, quando houver requisições posteriores.
  O spring interceptará a requisição, vai fazer o filtro com base nesta classe
  , e depois libera para os controller,
  por exemplo ProdutoController, ja identificando o cliente com base no token
*/
@Component
public class AutenticadorJWTFiltro extends OncePerRequestFilter {

    private final OperacoesTokenJWT geradorTokenJWT;
    private final ClienteRepository clienteRepository;

    public AutenticadorJWTFiltro(OperacoesTokenJWT geradorTokenJWT, ClienteRepository clienteRepository) {
        this.geradorTokenJWT = geradorTokenJWT;
        this.clienteRepository = clienteRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("FILTRO EXECUTADO");
        /*
          Obtém o cabeçalho Authorization.
        */
        String authorization = request.getHeader("Authorization");

        /*
          Caso não exista token enviado,
          a requisição continua normalmente.
        */
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        /*
          Remove o prefixo Bearer
          e mantém apenas o token.
        */
        String token = authorization.replace("Bearer ", "");

        /*
          Verifica se o token recebido
          continua válido.
        */
        boolean tokenValido = geradorTokenJWT.tokenValido(token);

        /*
          Caso seja inválido,
          a autenticação não prossegue.
        */
        if (!tokenValido) {
            filterChain.doFilter(request, response);
            return;
        }

        /*
          Extrai o login armazenado
          no campo subject do JWT.
        */
        String login = geradorTokenJWT.extrairLogin(token);
        System.out.println("LOGIN EXTRAIDO: " + login);

        /*
          Verifica se ainda existe um
          cliente relacionado ao login.
        */
        boolean clienteExiste = clienteRepository.findBylogin(login) != null;

        if (clienteExiste) {

            /*
              Registra o usuário autenticado
              dentro do contexto do Spring.
              A partir deste ponto qualquer
              classe pode recuperar o login
              do cliente autenticado.
            */
            UsernamePasswordAuthenticationToken autenticacao = new UsernamePasswordAuthenticationToken(
                    login, null,
                    Collections.emptyList());
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(autenticacao);
            System.out.println("USUARIO AUTENTICADO");
        }

        /*
          Continua o fluxo da requisição.
        */
        filterChain.doFilter(request, response);
    }
}
package com.projetobackendraizes.security;

import com.projetobackendraizes.entity.Cliente;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

   /*
     Classe responsável pelas operações com tokens de autenticação (JWT).
     Ela centraliza a criação do token que será utilizado para identificar
     e validar o usuário nas requisições após o login,
     já que o servidor não guarda estado, garantindo segurança
     e controle de acesso na aplicação.
   */
@Service
public class OperacoesTokenJWT {
    /*
      A notação @Value injeta o valor da chave
      configurada no application.properties
      para que possamos utilizá-la na geração
      e validação dos tokens JWT.
    */
    @Value("${jwt.secret}")
    private String secret;

    public String gerarToken(Cliente cliente) {

        return Jwts.builder()
                .subject(cliente.getLogin())
                .claim("role", "CLIENTE")
                .issuedAt(new Date())
                .expiration(
                        /*
                          86400000 é a quantidade de milisegundos que tem 24 horas.
                          Portanto, defini que a duração do token terá 24 horas
                          (o new Date pega o horário do momento e começa a contar
                          86400000 milisegundos), após esse tempo o token expira.
                        */
                        new Date(System.currentTimeMillis()
                                + 86400000))
                .signWith(
                        Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)),
                        Jwts.SIG.HS256)
                .compact();
    }

    /*
       Extrai o login armazenado no token.
       Durante a geração do JWT o login foi salvo
       no campo "subject". Portanto, ao ler o token,
       recuperamos esse valor para identificar
       qual cliente realizou a autenticação.
    */
    public String extrairLogin(String token) {

        return Jwts.parser()
                .verifyWith(
                        Keys.hmacShaKeyFor(
                                secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /*
       Verifica se o token é válido, quando o spring chamar doFilterInternal
       , após uma requição http.
       Caso a assinatura esteja correta e o token
       não esteja expirado, retorna true.
       Se ocorrer qualquer erro durante a leitura,
       considera-se que o token é inválido.
    */
    public boolean tokenValido(String token) {

        try {

            Jwts.parser()
                    .verifyWith(
                            Keys.hmacShaKeyFor(
                                    secret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }
}
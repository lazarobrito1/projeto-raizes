package com.projetobackendraizes.service;

import com.projetobackendraizes.dto.DadosParaAutenticarDTO;
import com.projetobackendraizes.entity.Cliente;
import com.projetobackendraizes.exceptions.CredenciaisInvalidasException;
import com.projetobackendraizes.repository.ClienteRepository;
import com.projetobackendraizes.security.OperacoesTokenJWT;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final ClienteRepository clienteRepository;
    private final BCryptPasswordEncoder encoder;
    private final OperacoesTokenJWT operacoesTokenJWT;

    public AuthService(ClienteRepository clienteRepository, BCryptPasswordEncoder encoder, OperacoesTokenJWT operacoesTokenJWT) {
        this.clienteRepository = clienteRepository;
        this.encoder = encoder;
        this.operacoesTokenJWT = operacoesTokenJWT;
    }

    /*
    O parametro tem como tipo de dado a classe
    que serve apenas para pegar dados de login
    (DadosParaAutenticarDTO)
    sem representar um cliente completo.
    é a classe DTO
    */
    public String autenticarCliente(DadosParaAutenticarDTO login) {

        /*
           Busca o cliente utilizando
           o login informado.
        */
        Cliente clienteBuscado = clienteRepository.findBylogin(login.getLogin());

        /*
           Caso não exista cliente
           relacionado ao login informado,
           a autenticação não pode prosseguir.
        */
        if (clienteBuscado == null) {
            throw new CredenciaisInvalidasException("Login ou senha invalidos");
        }


        /*
           O método matches compara a senha digitada
           com a senha armazenada em formato hash,
           retornando true para correspondência e
           false para divergência.
           o método matches é assinado como boolean,
           portanto, só devolve true ou false,
           que neste caso, é propício para verificação
           da igualdade/divergencia entra os objetos comparados.

        */
        boolean senhaValida = encoder.matches(login.getSenha(), clienteBuscado.getSenhaHash());

        /*
           Caso a senha não corresponda
           à hash armazenada, a autenticação
           deve ser interrompida.
        */
        if (!senhaValida) {
            throw new CredenciaisInvalidasException(
                    "Login ou senha invalidos");
        }

        /*
           Se login e senha estiverem corretos,
           o cliente é considerado autenticado.
           Nesse momento é gerado um token JWT
           contendo as informações necessárias
           para identificar o usuário nas próximas
           requisições sem que ele precise informar
           login e senha novamente.
        */
        return operacoesTokenJWT.gerarToken(clienteBuscado);
    }
}

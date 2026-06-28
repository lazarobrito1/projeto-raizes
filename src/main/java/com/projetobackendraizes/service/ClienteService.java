package com.projetobackendraizes.service;

import com.projetobackendraizes.entity.Cliente;
import com.projetobackendraizes.exceptions.ExcecaoNegocio;
import com.projetobackendraizes.exceptions.RecursoAusente;
import com.projetobackendraizes.repository.ClienteRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private BCryptPasswordEncoder encoder;

    public ClienteService(
            ClienteRepository clienteRepository,
            BCryptPasswordEncoder encoder) {

        this.clienteRepository = clienteRepository;
        this.encoder = encoder;
    }

    /*__________________________________________________________________________________*/


    public void cadastroCliente(Cliente cliente) {

        Cliente buscaRapida = clienteRepository.findBylogin(cliente.getLogin());

        /* Aqui, caso não haja retorno na busca, a variável será "null" */
        if (buscaRapida == null) {
            String senhaHasheada = encoder.encode(cliente.getSenhaHash());
            cliente.setSenhaHash(senhaHasheada);
            clienteRepository.save(cliente);

        } else {
            throw new ExcecaoNegocio("Login já existe");
        }
    }


    /*-----------------------------------------------------------------------------*/


    @Transactional /* Permite que as alterações ocorram de forma íntegra */
    public void atualizarDadosCliente(Cliente newDados) {

    /*
       Como findById retorna um Optional, podemos usar
       orElseThrow diretamente para extrair o cliente
       encontrado no banco.
       Isso simplifica o código porque não precisamos
       criar uma variável Optional, verificar se ela
       possui algo dentro e depois chamar get() para
       retirar o objeto da "caixinha".
       Caso o cliente não exista, a exceção será lançada.
       Caso exista, clienteDoBanco já conterá o cliente
       que será atualizado.
    */
        Cliente clienteDoBanco = clienteRepository
                .findById(newDados.getIdCliente())
                .orElseThrow(() ->
                        new RecursoAusente("Cliente não encontrado"));

    /*
       Verifica se o login informado na atualização
       já está sendo utilizado por outro cliente.
    */
        Cliente clienteComMesmoLogin =
                clienteRepository.findBylogin(newDados.getLogin());

    /*
       Se a busca retornar um cliente e o ID encontrado
       for diferente do cliente que está sendo atualizado,
       significa que o login já pertence a outra pessoa
       e não pode ser utilizado novamente.
    */
        if (clienteComMesmoLogin != null &&
                !clienteComMesmoLogin.getIdCliente()
                        .equals(clienteDoBanco.getIdCliente())) {

            throw new ExcecaoNegocio("Login já existe");
        }

        /* Atualiza os dados informados */
        clienteDoBanco.setLogin(newDados.getLogin());
        clienteDoBanco.setNome(newDados.getNome());

    /*
       A senha só será atualizada caso uma nova senha
       tenha sido informada durante a atualização.
    */

        if (newDados.getSenhaHash() != null && !newDados.getSenhaHash().isBlank()) /* Garantia de não estar nulo */ {

            /*
              O matches verifica se a senha
              nova digitada coincide com
              o hash antigo do banco
            */
            if (encoder.matches(newDados.getSenhaHash(), clienteDoBanco.getSenhaHash())) {
                throw new ExcecaoNegocio("A nova senha não pode ser igual à senha atual");
            }

            /* Se for uma senha nova de verdade, gera o hash e salva */
            String senhaAtualizadaHasheada = encoder.encode(newDados.getSenhaHash());
            clienteDoBanco.setSenhaHash(senhaAtualizadaHasheada);
        }
    /*
       Não é necessário chamar update manualmente.

       Como clienteDoBanco foi carregado pelo JPA,
       ele fica sendo monitorado durante a transação.

       Assim, quando algum atributo é alterado,
       o JPA percebe essas mudanças e realiza
       a atualização no banco automaticamente
       ao final da transação.
    */
    }

}



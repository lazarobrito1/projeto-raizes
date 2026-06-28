package com.projetobackendraizes.controller;

import com.projetobackendraizes.dto.AtualizarDadosDTO;
import com.projetobackendraizes.dto.DadosCadastroDTO;
import com.projetobackendraizes.entity.Cliente;
import com.projetobackendraizes.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/cadastrar")
    /*
    recebe dados ja tipados com a classe dto para transformar em um objeto cliente,
    de forma separada da entity cliente que cuida das regras de negocio.
    */
    public ResponseEntity<String> cadastrarCliente(@RequestBody DadosCadastroDTO dtoDeCadastro) {
        Cliente clienteNovo = new Cliente();
        clienteNovo.setNome(dtoDeCadastro.getNome());
        clienteNovo.setLogin(dtoDeCadastro.getLogin());
        clienteNovo.setSenhaHash(dtoDeCadastro.getSenha());

        clienteService.cadastroCliente(clienteNovo);

        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente cadastrado com sucesso.");
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarCliente(@RequestBody AtualizarDadosDTO AtualizarDadosDTO) {

        Cliente clienteAtualizandoDados = new Cliente();

        clienteAtualizandoDados.setIdCliente(AtualizarDadosDTO.getId());
        clienteAtualizandoDados.setNome(AtualizarDadosDTO.getNome());
        clienteAtualizandoDados.setLogin(AtualizarDadosDTO.getLogin());
        clienteAtualizandoDados.setSenhaHash(AtualizarDadosDTO.getSenha());

        clienteService.atualizarDadosCliente(clienteAtualizandoDados);
        return ResponseEntity.ok("Dados atualizados com sucesso.");
    }
}

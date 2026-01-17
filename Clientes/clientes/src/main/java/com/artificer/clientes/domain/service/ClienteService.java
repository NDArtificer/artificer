package com.artificer.clientes.domain.service;

import com.artificer.clientes.api.model.input.ClienteInput;
import com.artificer.clientes.domain.exception.ClienteNaoEncontradaException;
import com.artificer.clientes.domain.exception.NegocioException;
import com.artificer.clientes.domain.model.Cliente;
import com.artificer.clientes.domain.repository.ClienteRepository;
import com.artificer.clientes.mapper.ClienteMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    public Cliente buscarOuFalhar(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNaoEncontradaException("Cliente não encontrado com o ID: " + clienteId));
    }

    public Cliente buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email)
                .orElseThrow(() -> new ClienteNaoEncontradaException("Cliente não encontrado com o email: " + email));
    }

    @Transactional
    public Cliente salvar(Cliente cliente) {
        Optional<Cliente> clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
        if (clienteExistente.isPresent() && !clienteExistente.get().equals(cliente)) {
            throw new NegocioException(
                    String.format("Já existe um cliente cadastrado com o e-mail informado! %s", cliente.getEmail()));
        }

        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente atualizar(Long clienteId, ClienteInput clienteInput) {
        Cliente clienteAtual = buscarOuFalhar(clienteId);
        Cliente clienteAtualizado = clienteMapper.toEntity(clienteInput);
        clienteAtualizado.setId(clienteAtual.getId());
        return clienteRepository.save(clienteAtualizado);
    }

}

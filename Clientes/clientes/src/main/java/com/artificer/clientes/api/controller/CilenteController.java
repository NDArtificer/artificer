package com.artificer.clientes.api.controller;

import com.artificer.clientes.api.model.input.ClienteInput;
import com.artificer.clientes.api.model.output.ClientOutput;
import com.artificer.clientes.domain.model.Cliente;
import com.artificer.clientes.domain.repository.ClienteRepository;
import com.artificer.clientes.domain.service.ClienteService;
import com.artificer.clientes.mapper.ClienteMapper;
import com.artificer.clientes.page.CustomPage;
import com.artificer.clientes.page.PageMetaData;
import com.artificer.clientes.security.CanEditClientes;
import com.artificer.clientes.security.CanReadClientes;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/clientes")
public class CilenteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private ClienteService clientService;

    @GetMapping
    @CanReadClientes
    public ResponseEntity<CustomPage<ClientOutput>> findAll(@PageableDefault Pageable pageable) {
        Page<Cliente> clientesPage = clienteRepository.findAll(pageable);
        List<ClientOutput> clientOutputList = clienteMapper.toModel(clientesPage.getContent());
        CustomPage<ClientOutput> clientOutputCustomPage = new CustomPage<>(clientOutputList, PageMetaData.brandNewPage(clientesPage));
        return ResponseEntity.ok(clientOutputCustomPage);
    }

    @PostMapping
    @CanEditClientes
    public ResponseEntity<ClientOutput> createCliente(@RequestBody @Valid ClienteInput clienteInput) {
        Cliente cliente = clienteMapper.toEntity(clienteInput);
        Cliente clienteSalvo = clientService.salvar(cliente);
        ClientOutput clientOutput = clienteMapper.toModel(clienteSalvo);
        return ResponseEntity.status(CREATED).body(clientOutput);
    }

    @CanReadClientes
    @GetMapping("/{clienteId}")
    public ResponseEntity<ClientOutput> getClienteById(@PathVariable Long clienteId) {
        Cliente cliente = clientService.buscarOuFalhar(clienteId);
        ClientOutput clientOutput = clienteMapper.toModel(cliente);
        return ResponseEntity.ok(clientOutput);
    }


    @CanEditClientes
    @PutMapping("/{clienteId}")
    public ResponseEntity<ClientOutput> updateCliente(@PathVariable Long clienteId,
                                                      @RequestBody @Valid ClienteInput clienteInput) {
        Cliente clienteSalvo = clientService.atualizar(clienteId, clienteInput);
        ClientOutput clientOutput = clienteMapper.toModel(clienteSalvo);
        return ResponseEntity.ok(clientOutput);
    }


    @CanEditClientes
    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long clienteId) {
        clientService.buscarOuFalhar(clienteId);
        clienteRepository.deleteById(clienteId);
        return ResponseEntity.noContent().build();
    }

}

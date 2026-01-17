package com.artificer.clientes.mapper;

import com.artificer.clientes.api.model.input.ClienteInput;
import com.artificer.clientes.api.model.output.ClientOutput;
import com.artificer.clientes.domain.model.Cliente;
import com.artificer.clientes.domain.model.TipoCliente;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EnderecoMapper.class})
public interface ClienteMapper {

    @Mapping(target = "tipoCliente", expression = "java(tipo(cliente))")
    Cliente toEntity(ClienteInput clienteInput);

    List<ClientOutput> toModel(List<Cliente> clientes);

    @Mapping(target = "cpfCnpj", expression = "java(mask(cliente))")
    ClientOutput toModel(Cliente cliente);

    default String mask(Cliente cliente) {
        return TipoCliente.mask(cliente);
    }

    default TipoCliente tipo(Cliente cliente) {
        return cliente.getCpfCnpj().length() == 11 ? TipoCliente.CPF : TipoCliente.CNPJ;
    }
}

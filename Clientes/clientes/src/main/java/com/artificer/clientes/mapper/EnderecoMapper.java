package com.artificer.clientes.mapper;

import com.artificer.clientes.api.model.input.EnderecoInput;
import com.artificer.clientes.api.model.output.EnderecoOutput;
import com.artificer.clientes.domain.model.Endereco;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    Endereco toEntity(EnderecoInput enderecoInput);

    EnderecoOutput toModel(Endereco endereco);
}

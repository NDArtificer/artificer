package com.artificer.pedidos.mapper;

import com.artificer.pedidos.api.model.output.ClienteOutput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteOutput toModel(com.artificer.pedidos.domain.model.Cliente cliente);

}

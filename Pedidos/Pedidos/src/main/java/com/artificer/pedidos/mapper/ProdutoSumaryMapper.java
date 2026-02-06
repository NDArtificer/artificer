package com.artificer.pedidos.mapper;

import com.artificer.pedidos.api.model.input.ProdutoSumaryInput;
import com.artificer.pedidos.domain.model.Produto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdutoSumaryMapper {

    Produto toEntity(ProdutoSumaryInput produtoSumaryInput);
}

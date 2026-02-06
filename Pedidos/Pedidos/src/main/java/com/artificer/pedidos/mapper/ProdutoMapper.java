package com.artificer.pedidos.mapper;

import com.artificer.pedidos.api.model.input.ProdutoInput;
import com.artificer.pedidos.api.model.output.ProdutoOutput;
import com.artificer.pedidos.domain.model.Produto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    Produto toEntity(ProdutoInput produtoInput);

    ProdutoOutput toModel(Produto produto);

    List<ProdutoOutput> toModelList(List<Produto> produtos);

}

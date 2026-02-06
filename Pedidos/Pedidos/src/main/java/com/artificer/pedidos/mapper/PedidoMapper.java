package com.artificer.pedidos.mapper;

import com.artificer.pedidos.api.model.input.PedidoInput;
import com.artificer.pedidos.api.model.output.PedidoOutput;
import com.artificer.pedidos.domain.model.Pedido;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProdutoSumaryMapper.class, ProdutoMapper.class, ItemPedidoMapper.class, ClienteMapper.class})
public interface PedidoMapper {

    PedidoOutput toModel(Pedido pedido);

    Pedido toEntity(PedidoInput pedidoInput);

    List<PedidoOutput> toModel(List<Pedido> pedidos);


}

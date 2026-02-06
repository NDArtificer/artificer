package com.artificer.pedidos.mapper;

import com.artificer.pedidos.api.model.input.ItemPedidoInput;
import com.artificer.pedidos.api.model.output.ItemPedidoOutput;
import com.artificer.pedidos.domain.model.ItemPedido;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring", uses = {ProdutoMapper.class, ProdutoSumaryMapper.class})
public interface ItemPedidoMapper {

    ItemPedidoOutput toModel(ItemPedido itemPedido);
    ItemPedido toEntity(ItemPedidoInput itemPedidoInput);
    List<ItemPedidoOutput> toModel(List<ItemPedido> itensPedido);
    List<ItemPedido> toEntity(List<ItemPedidoInput> itensPedidoInput);


}
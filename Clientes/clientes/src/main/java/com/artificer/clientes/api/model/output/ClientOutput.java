package com.artificer.clientes.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientOutput {
    private Long id;
    private String nome;
    private String cpfCnpj;
    private String email;
    private String telefone;
    private EnderecoOutput endereco;
}

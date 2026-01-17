package com.artificer.clientes.api.model.input;

import com.artificer.clientes.validator.CPF_CNPJ;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteInput {

    @NotBlank
    private String nome;

    @Email
    private String email;

    @NotBlank
    private String telefone;

    @CPF_CNPJ
    private String cpfCnpj;

    @NotNull
    private EnderecoInput endereco;
}

package com.artificer.clientes.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static com.artificer.clientes.domain.model.TipoCliente.CPF;

@Getter
@Setter
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;
    private String cpfCnpj;
    private String email;
    private String telefone;

    @Embedded
    private Endereco endereco;

    @PrePersist
    private void definirTipoCliente() {
        this.tipoCliente = this.cpfCnpj != null && this.cpfCnpj.replaceAll("\\D", "").length() == 11
                ? CPF : TipoCliente.CNPJ;
    }
}

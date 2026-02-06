package com.artificer.pedidos.api.model.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteInput {

    @NotBlank
    @Email
    private String email;
}

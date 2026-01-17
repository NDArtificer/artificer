package com.artificer.clientes.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPF_CNPJValidator implements ConstraintValidator<CPF_CNPJ, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return false;

        String doc = value.replaceAll("\\D", "");

        if (doc.length() == 11) {
            return validarCPF(doc);
        } else if (doc.length() == 14) {
            return validarCNPJ(doc);
        }
        return false;
    }

    private boolean validarCPF(String cpf) {
        if (cpf.matches("(\\d)\\1{10}")) return false;

        int soma = 0;
        for (int i = 0; i < 9; i++) soma += (cpf.charAt(i) - '0') * (10 - i);
        int resto = (soma * 10) % 11;
        if (resto == 10) resto = 0;
        if (resto != (cpf.charAt(9) - '0')) return false;

        soma = 0;
        for (int i = 0; i < 10; i++) soma += (cpf.charAt(i) - '0') * (11 - i);
        resto = (soma * 10) % 11;
        if (resto == 10) resto = 0;
        return resto == (cpf.charAt(10) - '0');
    }

    private boolean validarCNPJ(String cnpj) {
        if (cnpj.matches("(\\d)\\1{13}")) return false;

        int[] peso1 = {5,4,3,2,9,8,7,6,5,4,3,2};
        int[] peso2 = {6,5,4,3,2,9,8,7,6,5,4,3,2};

        int soma = 0;
        for (int i = 0; i < 12; i++) soma += (cnpj.charAt(i) - '0') * peso1[i];
        int resto = soma % 11;
        int digito1 = (resto < 2) ? 0 : 11 - resto;

        soma = 0;
        for (int i = 0; i < 13; i++) soma += (cnpj.charAt(i) - '0') * peso2[i];
        resto = soma % 11;
        int digito2 = (resto < 2) ? 0 : 11 - resto;

        return digito1 == (cnpj.charAt(12) - '0') && digito2 == (cnpj.charAt(13) - '0');
    }
}


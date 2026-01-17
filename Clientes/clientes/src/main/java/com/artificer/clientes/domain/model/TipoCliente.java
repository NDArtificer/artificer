package com.artificer.clientes.domain.model;

public enum TipoCliente {
    CPF, CNPJ;

    public static String mask(Cliente cliente) {
        String cpfCnpj = cliente.getCpfCnpj();
        cpfCnpj = cpfCnpj.replaceAll("\\D", "");
        if (cliente.getTipoCliente().equals(CPF)) {
            return "******%s%s".formatted(cpfCnpj.substring(6, 9), cpfCnpj.substring(9));
        } else{
            return "********%s%s".formatted(cpfCnpj.substring(8, 12), cpfCnpj.substring(12));
        }
    }

}

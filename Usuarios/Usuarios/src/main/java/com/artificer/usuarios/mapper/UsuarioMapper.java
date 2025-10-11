package com.artificer.usuarios.mapper;

import com.artificer.usuarios.api.model.intput.UsuarioCredentials;
import com.artificer.usuarios.api.model.intput.UsuarioInput;
import com.artificer.usuarios.api.model.output.UsuarioOutput;
import com.artificer.usuarios.domain.model.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioCredentials toCredentials(Usuario usuario);
    UsuarioOutput toModel(Usuario usuario);
    List<UsuarioOutput> toModel(List<Usuario> usuarios);
    Usuario toEntity(UsuarioInput usuarioInput);

}

package com.artificer.usuarios.api.controller;

import com.artificer.usuarios.api.model.intput.UsuarioCredentials;
import com.artificer.usuarios.api.model.intput.UsuarioInput;
import com.artificer.usuarios.api.model.output.UsuarioOutput;
import com.artificer.usuarios.domain.model.Usuario;
import com.artificer.usuarios.domain.repository.UsuarioRepository;
import com.artificer.usuarios.domain.service.UsuarioService;
import com.artificer.usuarios.mapper.UsuarioMapper;
import com.artificer.usuarios.page.CustomPage;
import com.artificer.usuarios.page.PageMetaData;
import com.artificer.usuarios.security.CanEditUsuarios;
import com.artificer.usuarios.security.CanReadUsuarioCredentials;
import com.artificer.usuarios.security.CanReadUsuarios;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/usuarios")
public class UsuarioContoller {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @GetMapping
    @CanReadUsuarios
    public ResponseEntity<CustomPage<UsuarioOutput>> listarUsuarios(@PageableDefault Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
        List<UsuarioOutput> usuarioOutputs = usuarioMapper.toModel(usuarios.getContent());
        CustomPage<UsuarioOutput> customPage = new CustomPage<>(usuarioOutputs, PageMetaData.brandNewPage(usuarios));
        return ResponseEntity.ok(customPage);
    }

    @CanReadUsuarios
    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioOutput> buscarUsuario(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
        UsuarioOutput usuarioOutput = usuarioMapper.toModel(usuario);
        return ResponseEntity.ok(usuarioOutput);
    }


    @PostMapping
    @CanEditUsuarios
    public ResponseEntity<UsuarioOutput> cadastrarUsuario(@RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuario = usuarioMapper.toEntity(usuarioInput);
        var novousuario = usuarioService.salvar(usuario);
        UsuarioOutput usuarioOutput = usuarioMapper.toModel(novousuario);
        return ResponseEntity.status(CREATED).body(usuarioOutput);

    }

    @GetMapping("/email")
    @CanReadUsuarioCredentials
    public ResponseEntity<UsuarioCredentials> buscarUsuario(@RequestParam String email) {
        Usuario usuario = usuarioService.buscarOuFalhar(email);
        UsuarioCredentials usuarioCredentials = usuarioMapper.toCredentials(usuario);
        return ResponseEntity.ok(usuarioCredentials);
    }

}

package br.com.library.library.controller;

import br.com.library.library.controller.dto.UsuarioDTO;
import br.com.library.library.controller.mappers.UsuarioMapper;
import br.com.library.library.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/usuarios")
@RestController
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO dto) {
        var usuario = usuarioMapper.toEntity(dto);
        usuarioService.salvar(usuario);
    }
}

package br.com.library.library.controller.mappers;

import br.com.library.library.controller.dto.UsuarioDTO;
import br.com.library.library.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toEntity(UsuarioDTO dto);
}

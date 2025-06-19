package br.com.library.controller.mappers;

import br.com.library.controller.dto.UsuarioDTO;
import br.com.library.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toEntity(UsuarioDTO dto);
}

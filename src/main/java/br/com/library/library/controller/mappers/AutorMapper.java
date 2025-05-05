package br.com.library.library.controller.mappers;

import br.com.library.library.controller.dto.AutorDTO;
import br.com.library.library.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {
    Autor toEntity(AutorDTO dto);

    AutorDTO toDto(Autor entity);
}

package br.com.library.library.controller.mappers;

import br.com.library.library.controller.dto.CadastroLivroDTO;
import br.com.library.library.controller.dto.ResultadoPesquisaLivroDTO;
import br.com.library.library.model.Livro;
import br.com.library.library.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel="spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java(autorRepository.findById(dto.idAutor()).orElse(null))")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);

}

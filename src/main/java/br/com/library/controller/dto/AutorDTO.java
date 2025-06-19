package br.com.library.controller.dto;

import br.com.library.model.Autor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Autor")
public record AutorDTO(
        UUID id,
        @NotBlank(message = "campo obrigatorio nome")
        @Size(min = 2, max = 100, message = "campo fora do tamanho padrao")
        String nome,
        @NotNull (message = "campo obrigatorio dataNascimento")
        @Past(message = "não pode ser uma data futura")
        LocalDate dataNascimento,
        @NotBlank (message = "campo obrigatorio nacionalidade")
        @Size(max = 50, min = 2, message = "campo fora do tamanho padrao")
        String nacionalidade) {

    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}

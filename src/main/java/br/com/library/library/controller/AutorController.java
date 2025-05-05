package br.com.library.library.controller;

import br.com.library.library.controller.dto.AutorDTO;
import br.com.library.library.controller.dto.ErroResposta;
import br.com.library.library.controller.mappers.AutorMapper;
import br.com.library.library.controller.mappers.GenericController;
import br.com.library.library.exceptions.OperacaoNaoPermitidaException;
import br.com.library.library.exceptions.RegistroDuplicadoException;
import br.com.library.library.model.Autor;
import br.com.library.library.model.Usuario;
import br.com.library.library.repository.AutorRepository;
import br.com.library.library.security.SecurityService;
import br.com.library.library.service.AutorService;
import br.com.library.library.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController implements GenericController {

    private final AutorService autorService;
    private final AutorMapper autorMapper;
    public final SecurityService securityService;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO dto) {
       Autor autor = autorMapper.toEntity(dto);
       autorService.salvar(autor);
       URI location = gerarHeaderLocation(autor.getId());
       return ResponseEntity.ok(autor);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<AutorDTO> buscarPorId(@PathVariable String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.buscarPorId(idAutor);
        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            AutorDTO dto = new AutorDTO(
                    autor.getId(), autor.getNome(), autor.getDataNascimento(),autor.getNacionalidade()
            );
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Object> deletar(@PathVariable String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.buscarPorId(idAutor);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
            autorService.deletar(autorOptional.get());
            return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor>resultado = autorService.pesquisaByExemple(nome, nacionalidade);
        List<AutorDTO> dtos = resultado.stream().map(autor -> new AutorDTO(
                autor.getId(),autor.getNome(),autor.getDataNascimento(), autor.getNacionalidade()
        )).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Object> atualizar(@PathVariable String id, @RequestBody @Valid AutorDTO autorDTO) {
        try{
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.buscarPorId(idAutor);
        if (autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var autor = autorOptional.get();
        autor.setNome(autorDTO.nome());
        autor.setDataNascimento(autorDTO.dataNascimento());
        autor.setNacionalidade(autorDTO.nacionalidade());
        autorService.atualizar(autor);
        return ResponseEntity.noContent().build();
         } catch (RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.Status()).body(erroDTO);
        }
    }
}

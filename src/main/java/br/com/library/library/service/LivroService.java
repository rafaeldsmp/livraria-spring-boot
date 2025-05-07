package br.com.library.library.service;


import br.com.library.library.model.GeneroLivro;
import br.com.library.library.model.Livro;
import br.com.library.library.model.Usuario;
import br.com.library.library.repository.LivroRepository;
import br.com.library.library.repository.specs.LivroSpecs;
import br.com.library.library.security.SecurityService;
import br.com.library.library.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final SecurityService securityService;
    private final LivroValidator livroValidator;

    public Livro salvar(Livro livro) {
        livroValidator.validar(livro);
        Usuario usuario = securityService.obterUsuarioLogado();
        livro.setUsuario(usuario);
        livro.setTitulo(livro.getTitulo());
        return livroRepository.save(livro);
    }

   public Optional<Livro> obterPorId(UUID id) {
        return livroRepository.findById(id);
   }

   public void deletar(Livro livro) {
        livroRepository.delete(livro);
   }

   public Page<Livro> pesquisa(
           String isbn,
           String titulo,
           String nomeAutor,
           GeneroLivro genero,
           Integer anoPublicacao,
           Integer pagina,
           Integer tamanhoPagina){
        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if(isbn != null){
            specs = specs.and(LivroSpecs.isbnEquals(isbn));
        }
        if(titulo != null){
            specs = specs.and(LivroSpecs.tituloLike(titulo));
        }
        if(genero != null){
            specs = specs.and(LivroSpecs.generoEqual(genero));
        }
        if(anoPublicacao != null){
            specs = specs.and(LivroSpecs.anoPublicacaoEqual(anoPublicacao));
        }
        if(nomeAutor != null){
            specs = specs.and(LivroSpecs.nomeAutorLike(nomeAutor));
        }

       Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);
        return livroRepository.findAll(specs, pageRequest);
   }

    public void atualizar(Livro livro) {
        if(livro.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que o livro já esteja salvo");
        }
        livroValidator.validar(livro);
        livroRepository.save(livro);
    }
}

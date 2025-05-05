package br.com.library.library.validator;

import br.com.library.library.exceptions.CampoInvalidoException;
import br.com.library.library.exceptions.RegistroDuplicadoException;
import br.com.library.library.model.Livro;
import br.com.library.library.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;

    private final LivroRepository livroRepository;

    public void validar(Livro livro) {
        if(existemLivroComIsbn(livro)) {
            throw new RegistroDuplicadoException("ISBN já cadastrado!");
        }
        if(isPrecoObrigatorioNulo(livro)){
            throw new CampoInvalidoException("preco", "Para livros com ano de publicação a partir de 2020 o preço é obrigatório");
        }
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null && livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    private boolean existemLivroComIsbn(Livro livro){
        Optional<Livro> livroEncontrado = livroRepository.findByIsbn(livro.getIsbn());

        if(livro.getId() == null){
            return livroEncontrado.isPresent();
        }
        return livroEncontrado.map(Livro::getId).stream().anyMatch(id -> !id.equals(livro.getId()));
    }
}

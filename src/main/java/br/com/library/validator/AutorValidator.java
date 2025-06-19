package br.com.library.validator;

import br.com.library.exceptions.RegistroDuplicadoException;
import br.com.library.model.Autor;
import br.com.library.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    @Autowired
    private AutorRepository autorRepository;

    public void validar(Autor autor) {
        if(existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado!");
        }
    }

    private boolean  existeAutorCadastrado(Autor autor){
        Optional<Autor> autorEncontrado =
                autorRepository.findByNomeAndDataNascimentoAndNacionalidade(
                        autor.getNome().trim(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade());
        if(autor.getId() == null){
            return autorEncontrado.isPresent();
        }

        return autorEncontrado.isPresent() && !autor.getId().equals(autorEncontrado.get().getId());

    }
}
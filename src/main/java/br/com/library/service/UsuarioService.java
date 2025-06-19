package br.com.library.service;

import br.com.library.model.Usuario;
import br.com.library.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder enconder;

    public void salvar(Usuario usuario){
        var senha = usuario.getSenha();
        usuario.setSenha(enconder.encode(senha));
        usuarioRepository.save(usuario);
    }

    public Usuario obterPorLogin(String login){
        return usuarioRepository.findByLogin(login);
    }

    public Usuario obterPorEmail(String email){
        return usuarioRepository.findByEmail(email);
    }
}

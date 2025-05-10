package br.com.library.library.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioDTO(
        @NotBlank(message = "campo login obrigatorio")
        String login,
        @Email (message = "email inválido")
        @NotBlank(message = "email obrigatorio")
        String email,
        @NotBlank(message = "campo senha obrigatório")
        String senha,
        List<String> roles) {
}

package br.com.library.library.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Erro de Campo")
public record ErroCampo(String campo, String mensagem) {
}

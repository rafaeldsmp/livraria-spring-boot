package br.com.library.exceptions;

import lombok.Getter;

@Getter
public class CampoInvalidoException extends RuntimeException{
    String campo;

    public CampoInvalidoException(String campo, String mensagem) {
        super(mensagem);
        this.campo = campo;
    }
}

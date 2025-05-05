package br.com.library.library.exceptions;

public class RegistroDuplicadoException extends RuntimeException {
    public RegistroDuplicadoException(String message) {
        super(message);
    }
}

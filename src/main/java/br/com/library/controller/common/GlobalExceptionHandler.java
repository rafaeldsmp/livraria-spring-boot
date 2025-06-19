package br.com.library.controller.common;

import br.com.library.controller.dto.ErroCampo;
import br.com.library.controller.dto.ErroResposta;
import br.com.library.exceptions.CampoInvalidoException;
import br.com.library.exceptions.OperacaoNaoPermitidaException;
import br.com.library.exceptions.RegistroDuplicadoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        log.error("Erro de validação {}", exception.getMessage());
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<ErroCampo> listaErros = fieldErrors.stream().map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage())).toList();
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação.", listaErros);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistroDuplicadoexception(RegistroDuplicadoException exception) {
        return ErroResposta.conflito(exception.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException exception) {
        return ErroResposta.respostaPadrao(exception.getMessage());
    }

    @ExceptionHandler(CampoInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleCampoInvalidoException(CampoInvalidoException exception) {
        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação.",
                List.of(new ErroCampo(exception.getCampo(), exception.getMessage()))
        );
    }


    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErroResposta handleAccesDeniedException(AccessDeniedException exception){
        return new ErroResposta(HttpStatus.FORBIDDEN.value(), "Acesso Negado", List.of());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleErrosNaoTratados(RuntimeException e) {
        log.error("Erro inesperado: {}", e.getMessage());
        return new ErroResposta(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocorreu um erro inesperado. Entre em contato com a administração", List.of());
    }

}
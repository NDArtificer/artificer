package com.artificer.pedidos.handler;

import com.artificer.pedidos.domain.exception.ClienteNaoEncontradoException;
import com.artificer.pedidos.domain.exception.NegocioException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String URL = "https:/%s.com/erros/";

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        String endpointChamado = servletRequest.getRequestURI();
        String metodoHttp = servletRequest.getMethod();

        var problemDetails = ProblemDetail.forStatus(status);
        problemDetails.setTitle("Um ou mais campos do payload estão inválidos!");
        problemDetails.setType(URI.create("%scampos-invalidos".formatted(URL.formatted(endpointChamado))));
        problemDetails.setProperty("method", metodoHttp);

        var fieldErros = ex.getBindingResult().getAllErrors().stream().collect(Collectors.toMap(
                objectError -> ((FieldError) objectError).getField(),
                objectError -> messageSource.getMessage(objectError, LocaleContextHolder.getLocale())));
        problemDetails.setProperty("fieldErros", fieldErros);

        return super.handleExceptionInternal(ex, problemDetails, headers, status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ProblemDetail handleNegocioException(NegocioException e, WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        String endpointChamado = servletRequest.getRequestURI();
        String metodoHttp = servletRequest.getMethod();

        var problemDetail = ProblemDetail.forStatus(BAD_REQUEST);
        problemDetail.setTitle(e.getMessage());
        problemDetail.setType(URI.create("%sregra-de-negocio-violada".formatted(URL.formatted(endpointChamado))));
        problemDetail.setProperty("method", metodoHttp);
        return problemDetail;
    }

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ProblemDetail handleEntidadeNaoEncontrada(ClienteNaoEncontradoException e, WebRequest request) {

        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        String endpointChamado = servletRequest.getRequestURI();
        String metodoHttp = servletRequest.getMethod();
        var problemDetail = ProblemDetail.forStatus(NOT_FOUND);
        problemDetail.setTitle(e.getMessage());
        problemDetail.setType(URI.create("%srecurso-nao-encontrado".formatted(URL.formatted(endpointChamado))));
        problemDetail.setProperty("method", metodoHttp);
        return problemDetail;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolation(DataIntegrityViolationException e, WebRequest request) {

        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        String endpointChamado = servletRequest.getRequestURI();
        String metodoHttp = servletRequest.getMethod();
        var problemDetail = ProblemDetail.forStatus(CONFLICT);
        problemDetail.setTitle("Recurso a ser excluído está em uso!");
        problemDetail.setType(URI.create("%srecurso-em-uso".formatted(URL.formatted(endpointChamado))));
        problemDetail.setProperty("method", metodoHttp);
        return problemDetail;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException e, WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        String endpointChamado = servletRequest.getRequestURI();
        String metodoHttp = servletRequest.getMethod();
        var problemDetail = ProblemDetail.forStatus(FORBIDDEN);
        problemDetail.setTitle("Accesso Negado, Privilégios insuficientes!");
        problemDetail.setType(URI.create("%sacesso-negado".formatted(URL.formatted(endpointChamado))));
        problemDetail.setProperty("method", metodoHttp);
        return problemDetail;
    }

    @ExceptionHandler(WebClientResponseException.NotFound.class)
    public ProblemDetail handleClienteNaoEncontrado(WebClientResponseException.NotFound e, WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        String endpointChamado = servletRequest.getRequestURI();
        String metodoHttp = servletRequest.getMethod();
        var problemDetail = ProblemDetail.forStatus(NOT_FOUND);
        problemDetail.setTitle("Cliente informado não foi encontrado!");
        problemDetail.setType(URI.create("%s/recurso-nao-encontrado".formatted(URL.formatted(endpointChamado))));
        problemDetail.setProperty("method", metodoHttp);
        return problemDetail;
    }


}

package br.com.api.cadastrousuario.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Usuário não encontrado
    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleUsuarioNaoEncontradoException(UsuarioNaoEncontradoException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Usuário não encontrado", ex.getMessage());
    }

    // Usuário já cadastrado
    @ExceptionHandler(UsuarioJaCadastradoException.class)
    public ResponseEntity<Map<String, Object>> handleUsuarioJaCadastradoException(UsuarioJaCadastradoException ex) {
        return buildResponse(HttpStatus.CONFLICT, "Conflito no banco de dados", ex.getMessage());
    }

    // CPF inválido
    @ExceptionHandler(CpfInvalidoException.class)
    public ResponseEntity<Map<String, Object>> handleCpfInvalidoException(CpfInvalidoException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "CPF inválido", ex.getMessage());
    }

    // Data de Nascimento inválida
    @ExceptionHandler(DataNascimentoInvalidaException.class)
    public ResponseEntity<Map<String, Object>> handleDataNascimentoInvalidaException(DataNascimentoInvalidaException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Data de nascimento inválida", ex.getMessage());
    }


    // Parâmetro obrigatório ausente
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, Object>> handleMissingParams(MissingServletRequestParameterException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Parâmetro obrigatório ausente", ex.getMessage());
    }

    // Requisição inválida (ex: JSON malformado)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleJsonError(HttpMessageNotReadableException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Erro na requisição", "O JSON enviado está malformado ou contém valores inválidos.");
    }

    // Validação de argumentos inválidos (ex: @Valid falhando)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Erro de validação", ex.getBindingResult().getFieldError().getDefaultMessage());
    }

    // Violação de integridade no banco (ex: CPF duplicado)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDatabaseException(DataIntegrityViolationException ex) {
        return buildResponse(HttpStatus.CONFLICT, "Conflito no banco de dados", "Já existe um usuário com esse CPF.");
    }

    // Tratamento genérico para IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Parâmetro inválido", ex.getMessage());
    }

    // Tratamento genérico para exceções não previstas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor", "Ocorreu um erro inesperado.");
    }

    // Méthodo auxiliar para formatar a resposta
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String error, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", error);
        response.put("message", message);
        return ResponseEntity.status(status).body(response);
    }
}

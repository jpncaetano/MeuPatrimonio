package br.com.api.cadastrousuario.exception;

public class UsuarioJaCadastradoException extends RuntimeException {
    public UsuarioJaCadastradoException(String message) {
        super(message);
    }
}

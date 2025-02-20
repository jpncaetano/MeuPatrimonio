package br.com.api.cadastrousuario.exception;

public class CpfInvalidoException extends RuntimeException {
    public CpfInvalidoException(String message) {
        super(message);
    }
}

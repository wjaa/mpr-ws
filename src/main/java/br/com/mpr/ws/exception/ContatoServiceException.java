package br.com.mpr.ws.exception;

public class ContatoServiceException extends ServiceException {

    public ContatoServiceException(String message) {
        super(message);
    }

    public ContatoServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

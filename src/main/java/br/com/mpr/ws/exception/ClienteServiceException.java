package br.com.mpr.ws.exception;

/**
 *
 */
public class ClienteServiceException extends Exception {

    public ClienteServiceException(String message) {
        super(message);
    }

    public ClienteServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

package br.com.mpr.ws.exception;

public class PagSeguroNotificationException extends RuntimeException {

    public PagSeguroNotificationException(String message) {
        super(message);
    }

    public PagSeguroNotificationException(String message, Throwable cause) {
        super(message, cause);
    }
}

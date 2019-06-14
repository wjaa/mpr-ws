package br.com.mpr.ws.exception;

public class SessionServiceException extends ServiceException {
    public SessionServiceException(String message) {
        super(message);
    }

    public SessionServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

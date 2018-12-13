package br.com.mpr.ws.exception;

public class LoginServiceException extends ServiceException {

    public LoginServiceException(String message) {
        super(message);
    }

    public LoginServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

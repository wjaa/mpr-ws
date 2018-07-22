package br.com.mpr.ws.exception;

/**
 * Created by wagner on 7/13/18.
 */
public abstract class ServiceException extends Exception {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

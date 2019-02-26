package br.com.mpr.ws.exception;

/**
 * Created by wagner on 16/02/19.
 */
public class EstoqueServiceException extends ServiceException {

    public EstoqueServiceException(String message) {
        super(message);
    }

    public EstoqueServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

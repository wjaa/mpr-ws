package br.com.mpr.ws.exception;



/**
 * Created by wagner on 6/25/18.
 */
public class AdminServiceException extends ServiceException {

    public AdminServiceException(String message) {
        super(message);
    }

    public AdminServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

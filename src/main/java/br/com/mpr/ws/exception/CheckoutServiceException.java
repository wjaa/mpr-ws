package br.com.mpr.ws.exception;

/**
 * Created by wagner on 13/01/19.
 */
public class CheckoutServiceException extends ServiceException {

    public CheckoutServiceException(String message) {
        super(message);
    }

    public CheckoutServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

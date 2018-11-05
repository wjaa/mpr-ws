package br.com.mpr.ws.exception;

/**
 *
 */
public class CarrinhoServiceException extends ServiceException {


    public CarrinhoServiceException(String message) {
        super(message);
    }

    public CarrinhoServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

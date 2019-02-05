package br.com.mpr.ws.exception;

/**
 * Created by wagner on 04/02/19.
 */
public class PedidoServiceException extends ServiceException {

    public PedidoServiceException(String message) {
        super(message);
    }

    public PedidoServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

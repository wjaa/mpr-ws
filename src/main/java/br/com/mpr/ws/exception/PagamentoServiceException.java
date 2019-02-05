package br.com.mpr.ws.exception;

/**
 *
 */
public class PagamentoServiceException extends ServiceException {
    public PagamentoServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PagamentoServiceException(String message) {
        super(message);
    }
}

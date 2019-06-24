package br.com.mpr.ws.exception;

/**
 *
 */
public class ProdutoPreviewServiceException extends ServiceException {

    public ProdutoPreviewServiceException(String message) {
        super(message);
    }

    public ProdutoPreviewServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

package br.com.mpr.ws.exception;


/**
 *
 */
public class ImagemServiceException extends ServiceException {

    public ImagemServiceException(String message) {
        super(message);
    }

    public ImagemServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

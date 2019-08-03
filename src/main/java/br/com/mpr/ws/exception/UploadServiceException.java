package br.com.mpr.ws.exception;

/**
 *
 */
public class UploadServiceException extends ServiceException {

    public UploadServiceException(String message) {
        super(message);
    }

    public UploadServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

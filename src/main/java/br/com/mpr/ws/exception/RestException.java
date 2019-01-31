package br.com.mpr.ws.exception;

import br.com.mpr.ws.vo.ErrorMessageVo;

public class RestException extends Exception {

    private ErrorMessageVo errorMessage;


    public RestException(String msg) {
        super(msg);
    }

    public RestException(ErrorMessageVo errorMessage) {
        this(errorMessage.getErrorDetail());
        this.errorMessage = errorMessage;
    }



    public ErrorMessageVo getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessageVo errorMessage) {
        this.errorMessage = errorMessage;
    }

}

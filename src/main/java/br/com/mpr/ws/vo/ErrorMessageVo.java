package br.com.mpr.ws.vo;

/**
 * Created by wagner on 18/06/15.
 */
public class ErrorMessageVo {

    private Integer errorCode;
    private String errorMessage;


    public ErrorMessageVo() {
    }

    public ErrorMessageVo(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

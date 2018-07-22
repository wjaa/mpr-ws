package br.com.mpr.ws.vo;

import java.util.Date;

/**
 * Created by wagner on 18/06/15.
 */
public class ErrorMessageVo {

    private Integer errorCode;
    private String [] errorMessage;
    private String errorDetail;
    private Date timestamp;

    public ErrorMessageVo(Integer errorCode, Date timestamp, String errorDetail, String ... errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorDetail = errorDetail;
        this.timestamp = timestamp;
    }

    public ErrorMessageVo() {
    }

    public ErrorMessageVo(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = new String[]{errorMessage};
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String[] getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String[] errorMessage) {
        this.errorMessage = errorMessage;
    }
}

package br.com.mpr.ws.vo;

import br.com.mpr.ws.helper.JacksonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * Created by wagner on 18/06/15.
 */
public class ErrorMessageVo {

    private Integer errorCode;
    private String [] errorMessage;
    private String errorDetail;

    @JsonSerialize(using = JacksonDateTimeSerializer.class)
    private Date time;

    public ErrorMessageVo(Integer errorCode, Date timestamp, String errorDetail, String ... errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorDetail = errorDetail;
        this.time = timestamp;
    }

    public ErrorMessageVo() {
    }

    public ErrorMessageVo(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = new String[]{errorMessage};
        this.time = new Date();
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String[] getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String[] errorMessage) {
        this.errorMessage = errorMessage;
    }
}

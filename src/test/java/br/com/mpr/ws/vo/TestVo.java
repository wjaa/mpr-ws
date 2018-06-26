package br.com.mpr.ws.vo;

import javax.persistence.*;
import java.util.Date;

/**
 *
 */
public class TestVo {

    private Long id;
    private String testVarchar;
    private Boolean testBoolean;
    private Double testDouble;
    private Integer testInt;
    private Long testLong;
    private Date testDate;
    private Date testDateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestVarchar() {
        return testVarchar;
    }

    public void setTestVarchar(String testVarchar) {
        this.testVarchar = testVarchar;
    }

    public Boolean getTestBoolean() {
        return testBoolean;
    }

    public void setTestBoolean(Boolean testBoolean) {
        this.testBoolean = testBoolean;
    }

    public Double getTestDouble() {
        return testDouble;
    }

    public void setTestDouble(Double testDouble) {
        this.testDouble = testDouble;
    }

    public Integer getTestInt() {
        return testInt;
    }

    public void setTestInt(Integer testInt) {
        this.testInt = testInt;
    }

    public Long getTestLong() {
        return testLong;
    }

    public void setTestLong(Long testLong) {
        this.testLong = testLong;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public Date getTestDateTime() {
        return testDateTime;
    }

    public void setTestDateTime(Date testDateTime) {
        this.testDateTime = testDateTime;
    }
}

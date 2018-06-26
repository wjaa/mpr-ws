package br.com.mpr.ws.entity;

import javax.persistence.*;
import java.util.Date;

/**
 *
 */
@Entity
@Table(name = "TABLE_TEST")
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "TEST_VARCHAR", length = 255)
    private String testVarchar;

    @Column(name = "TEST_BOOLEAN", precision = 1)
    private Boolean testBoolean;

    @Column(name = "TEST_DOUBLE", precision = 6 , scale = 2)
    private Double testDouble;

    @Column(name = "TEST_INT", precision = 6 , scale = 2)
    private Integer testInt;

    @Column(name = "TEST_LONG", precision = 6 , scale = 2)
    private Long testLong;

    @Column(name = "TEST_DATE")
    @Temporal(TemporalType.DATE)
    private Date testDate;

    @Column(name = "TEST_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
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

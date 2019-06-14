package br.com.mpr.ws.entity;

import br.com.mpr.ws.helper.JacksonDateTimeDeserializer;
import br.com.mpr.ws.helper.JacksonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.util.JsonDateDeserializer;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wagner on 6/17/19.
 */
@Entity
@Table(name = "SESSION")
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "SESSION_TOKEN", nullable = false, length = 64)
    private String sessionToken;

    @Column(name = "EXPIRATION_DATE", nullable = false)
    @JsonSerialize(using = JacksonDateTimeSerializer.class)
    private Date expirationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}

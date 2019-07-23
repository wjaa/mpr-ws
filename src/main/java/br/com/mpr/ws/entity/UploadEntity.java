package br.com.mpr.ws.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

//@Entity
public class UploadEntity {

    @Id
    private Long id;
    private String token;

    @JsonIgnore
    private List<ImagemEntity> imagens;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<ImagemEntity> getImagens() {
        return imagens;
    }

    public void setImagens(List<ImagemEntity> imagens) {
        this.imagens = imagens;
    }
}

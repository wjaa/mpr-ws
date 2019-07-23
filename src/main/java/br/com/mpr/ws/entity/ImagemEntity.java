package br.com.mpr.ws.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class ImagemEntity {

    @Id
    private Long id;
    private Long idUpload;
    private String imagem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUpload() {
        return idUpload;
    }

    public void setIdUpload(Long idUpload) {
        this.idUpload = idUpload;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}

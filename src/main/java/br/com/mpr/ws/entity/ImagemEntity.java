package br.com.mpr.ws.entity;

import javax.persistence.*;

@Entity
@Table(name = "IMAGEM")
public class ImagemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ID_UPLOAD", nullable = false)
    private Long idUpload;

    @Column(name = "IMAGEM_HI", nullable = false)
    private String imagemHi;

    @Column(name = "IMAGEM_THUMB", nullable = false)
    private String imagemThumb;

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

    public String getImagemHi() {
        return imagemHi;
    }

    public void setImagemHi(String imagemHi) {
        this.imagemHi = imagemHi;
    }

    public String getImagemThumb() {
        return imagemThumb;
    }

    public void setImagemThumb(String imagemThumb) {
        this.imagemThumb = imagemThumb;
    }
}

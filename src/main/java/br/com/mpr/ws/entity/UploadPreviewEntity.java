package br.com.mpr.ws.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "UPLOAD_PREVIEW")
public class UploadPreviewEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ID_UPLOAD", nullable = false)
    private Long idUpload;

    @Column(name = "ID_PRODUTO", nullable = false)
    private Long idProduto;

    @Column(name = "IMAGEM", nullable = false)
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

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}

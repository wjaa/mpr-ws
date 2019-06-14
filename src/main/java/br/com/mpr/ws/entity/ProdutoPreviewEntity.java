package br.com.mpr.ws.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by wagner on 6/17/19.
 */
@Entity
@Table(name = "PRODUTO_PREVIEW")
public class ProdutoPreviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ID_SESSION")
    private Long idSession;

    @Column(name = "ID_CLIENTE")
    private Long idCliente;

    @Column(name = "ID_PRODUTO", nullable = false)
    private Long idProduto;

    @Column(name = "FOTO_PREVIEW", nullable = false)
    private String fotoPreview;

    @Transient
    private List<ProdutoPreviewAnexoEntity> anexos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdSession() {
        return idSession;
    }

    public void setIdSession(Long idSession) {
        this.idSession = idSession;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public List<ProdutoPreviewAnexoEntity> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<ProdutoPreviewAnexoEntity> anexos) {
        this.anexos = anexos;
    }

    public String getFotoPreview() {
        return fotoPreview;
    }

    public void setFotoPreview(String fotoPreview) {
        this.fotoPreview = fotoPreview;
    }
}

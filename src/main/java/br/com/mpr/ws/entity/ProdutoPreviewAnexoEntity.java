package br.com.mpr.ws.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.StringUtils;

import javax.persistence.*;

/**
 *
 */

@Entity
@Table(name = "PRODUTO_PREVIEW_ANEXO")
public class ProdutoPreviewAnexoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ID_PRODUTO_PREVIEW", nullable = false)
    private Long idProdutoPreview;

    @Column(name = "FOTO", length = 200)
    private String foto;

    @Column(name = "ID_CATALOGO")
    private Long idCatalogo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProdutoPreview() {
        return idProdutoPreview;
    }

    public void setIdProdutoPreview(Long idProdutoPreview) {
        this.idProdutoPreview = idProdutoPreview;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Long getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(Long idCatalogo) {
        this.idCatalogo = idCatalogo;
    }


    @Transient
    @JsonIgnore
    public boolean temCatalogo(){
        return this.idCatalogo != null;
    }

    @Transient
    @JsonIgnore
    public boolean temFoto(){
        return !StringUtils.isEmpty(this.foto);
    }
}

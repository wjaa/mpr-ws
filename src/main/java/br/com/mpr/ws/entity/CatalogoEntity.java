package br.com.mpr.ws.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 */
@Entity
@Table(name = "CATALOGO")
public class CatalogoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ID_CATALOGO_GRUPO", nullable = false)
    @NotNull(message = "Grupo é obrigatório!")
    private Long idCatalogoGrupo;

    @Column(name = "DESCRICAO", nullable = false, length = 50)
    @NotNull(message = "Descrição da foto é obrigatória!")
    private String descricao;

    @Column(name = "IMG", nullable = false, length = 100)
    private String img;

    @ManyToOne
    @JoinColumn(name = "ID_CATALOGO_GRUPO", updatable = false, insertable = false)
    private CatalogoGrupoEntity catalogoGrupo;

    @Column(name = "ATIVO", nullable = false)
    @NotNull(message = "Campo ativo não pode ser nulo.")
    private Boolean ativo;

    @Transient
    private byte [] byteImg;

    @Transient
    private String nameImg;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCatalogoGrupo() {
        return idCatalogoGrupo;
    }

    public void setIdCatalogoGrupo(Long idCatalogoGrupo) {
        this.idCatalogoGrupo = idCatalogoGrupo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public CatalogoGrupoEntity getCatalogoGrupo() {
        return catalogoGrupo;
    }

    public void setCatalogoGrupo(CatalogoGrupoEntity catalogoGrupo) {
        this.catalogoGrupo = catalogoGrupo;
    }

    public byte[] getByteImg() {
        return byteImg;
    }

    public void setByteImg(byte[] byteImg) {
        this.byteImg = byteImg;
    }

    public String getNameImg() {
        return nameImg;
    }

    public void setNameImg(String nameImg) {
        this.nameImg = nameImg;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}

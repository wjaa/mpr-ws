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
}

package br.com.mpr.ws.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * Created by wagner on 13/01/19.
 */
@Entity
@Table(name = "EMBALAGEM")
public class EmbalagemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "DESCRICAO", nullable = false, length = 50)
    @NotEmpty(message = "Descrição é obrigatório!")
    private String descricao;

    @Column(name = "COMP", nullable = false)
    @NotEmpty(message = "Comprimento do produto é obrigatório!")
    private Double comp;

    @Column(name = "LARG", nullable = false)
    @NotEmpty(message = "Largura do produto é obrigatório!")
    private Double larg;

    @Column(name = "ALT", nullable = false)
    @NotEmpty(message = "Altura do produto é obrigatório!")
    private Double alt;

    @Column(name = "ATIVO", nullable = false)
    private Boolean ativo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getComp() {
        return comp;
    }

    public void setComp(Double comp) {
        this.comp = comp;
    }

    public Double getLarg() {
        return larg;
    }

    public void setLarg(Double larg) {
        this.larg = larg;
    }

    public Double getAlt() {
        return alt;
    }

    public void setAlt(Double alt) {
        this.alt = alt;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}

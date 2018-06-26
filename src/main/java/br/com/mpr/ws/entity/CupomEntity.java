package br.com.mpr.ws.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wagner on 04/06/18.
 */
@Entity
@Table(name = "CUPOM_DESCONTO")
public class CupomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "DESCRICAO", nullable = false, length = 50)
    private String descricao;

    @Column(name = "HASH", nullable = false, length = 8)
    private String hash;

    @Column(name = "DATA_INICIO", nullable = false)
    private Date dataInicio;

    @Column(name = "DATA_FIM", nullable = false)
    private Date dataFim;

    @Column(name = "PROMOCAO", nullable = false)
    private Boolean promocao;

    @Column(name = "PORCENTAGEM", nullable = false, scale = 3, precision = 2)
    private Double porcentagem;


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


    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }


    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }


    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }


    public Boolean getPromocao() {
        return promocao;
    }

    public void setPromocao(Boolean promocao) {
        this.promocao = promocao;
    }


    public Double getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(Double porcentagem) {
        this.porcentagem = porcentagem;
    }
}

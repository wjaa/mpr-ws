package br.com.mpr.ws.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wagner on 04/06/18.
 */
@Entity
@Table(name = "CUPOM_DESCONTO")
public class CupomEntity {

    private Long id;
    private String descricao;
    private String hash;
    private Date dataInicio;
    private Date dataFim;
    private Boolean promocao;
    //se nao for promocao essa quantidade tem que ter no maximo 1x
    private Integer qtdeUtilizada;
    private Double porcentagem;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "DESCRICAO", nullable = false, length = 50)
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Column(name = "HASH", nullable = false, length = 8)
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Column(name = "DATA_INICIO", nullable = false)
    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    @Column(name = "DATA_FIM", nullable = false)
    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    @Column(name = "PROMOCAO", nullable = false)
    public Boolean getPromocao() {
        return promocao;
    }

    public void setPromocao(Boolean promocao) {
        this.promocao = promocao;
    }

    @Column(name = "QTDE_UTILIZADA")
    public Integer getQtdeUtilizada() {
        return qtdeUtilizada;
    }

    public void setQtdeUtilizada(Integer qtdeUtilizada) {
        this.qtdeUtilizada = qtdeUtilizada;
    }


    @Column(name = "PORCENTAGEM", nullable = false, scale = 5, precision = 2)
    public Double getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(Double porcentagem) {
        this.porcentagem = porcentagem;
    }
}

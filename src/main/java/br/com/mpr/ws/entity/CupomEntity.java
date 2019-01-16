package br.com.mpr.ws.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by wagner on 04/06/18.
 */
@Entity
@Table(name = "CUPOM_DESCONTO")
public class CupomEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "DESCRICAO", nullable = false, length = 50)
    @NotEmpty(message = "Descrição é obrigatório!")
    private String descricao;

    @Column(name = "HASH", nullable = false, length = 8)
    @Length(max = 8, message = "Tamanho máximo para o código do cupom é de 8 caracteres.")
    private String hash;

    @Column(name = "DATA_INICIO", nullable = false)
    @NotNull(message = "Data inicial é obrigatório!")
    private Date dataInicio;

    @Column(name = "DATA_FIM", nullable = false)
    @NotNull(message = "Data final é obrigatório!")
    private Date dataFim;

    @Column(name = "PROMOCAO", nullable = false)
    private Boolean promocao;

    @Column(name = "PORCENTAGEM", nullable = false, scale = 4, precision = 2)
    @NotNull(message = "Porcentagem é obrigatório!")
    private Double porcentagem;

    @Column(name = "QUANTIDADE", nullable = false)
    @NotNull(message = "Quantidade é obrigatória!")
    private Integer quantidade;


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
        return promocao != null ? promocao : false;
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

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}

package br.com.mpr.ws.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wagner on 13/06/18.
 */
@Entity
@Table(name = "ESTOQUE")
public class EstoqueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ID_PRODUTO", nullable = false)
    private Long idProduto;

    @Column(name = "ID_FORNECEDOR", nullable = false)
    private Long idFornecedor;

    @Column(name = "DATA_COMPRA", nullable = false)
    private Date dataCompra;

    @Column(name = "DATA_ATUALIZACAO", nullable = false)
    private Date dataAtualizacao;

    @Column(name = "PRECO_COMPRA", nullable = false, scale = 6 , precision = 2)
    private Double precoCompra;

    @Column(name = "INVALIDO")
    private Boolean invalido;

    @Column(name = "OBSERVACAO", length = 60)
    private String observacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public Long getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Long idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(Double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public Boolean getInvalido() {
        return invalido;
    }

    public void setInvalido(Boolean invalido) {
        this.invalido = invalido;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}

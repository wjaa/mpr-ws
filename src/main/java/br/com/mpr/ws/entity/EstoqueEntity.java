package br.com.mpr.ws.entity;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by wagner on 13/06/18.
 */
@Entity
@Table(name = "ESTOQUE")
public class EstoqueEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "estoque", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<EstoqueProdutoEntity> produtos;

    @ManyToOne
    @JoinColumn(name = "ID_FORNECEDOR", updatable = false, insertable = false)
    private FornecedorEntity fornecedor;

    @NotNull(message = "Fornecedor é obrigatório!")
    @Column(name = "ID_FORNECEDOR", nullable = false)
    private Long idFornecedor;


    @NotNull(message = "Data da compra é obrigatório!")
    @Column(name = "DATA_COMPRA", nullable = false)
    private Date dataCompra;

    @Column(name = "DATA_ATUALIZACAO", nullable = false)
    private Date dataAtualizacao;


    @NotNull(message = "Preço de compra é obrigatório!")
    @Range(min = 0, max = 999999)
    @Column(name = "PRECO_COMPRA", nullable = false, scale = 6 , precision = 2)
    private Double precoCompra;

    @Column(name = "OBSERVACAO", length = 60)
    private String observacao;

    @Transient
    @NotNull(message = "Quantidade é obrigatória!")
    @Min(value = 1, message = "Quantidade mínima do lote do estoque é 1 produto.")
    @Max(value = 100, message = "Quantidade máxima do lote é de 100 produtos.")
    private Integer quantidade;

    @Transient
    @NotNull(message = "Produto é obrigatório!")
    private Long idProduto;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public FornecedorEntity getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(FornecedorEntity fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public List<EstoqueProdutoEntity> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<EstoqueProdutoEntity> produtos) {
        this.produtos = produtos;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }
}

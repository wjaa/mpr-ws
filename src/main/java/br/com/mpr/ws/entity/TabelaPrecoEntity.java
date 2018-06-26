package br.com.mpr.ws.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wagner on 04/06/18.
 */
@Entity
@Table(name = "TABELA_PRECO")
public class TabelaPrecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ID_PRODUTO", nullable = false)
    private Long idProduto;

    @Column(name = "DATA_VIGENCIA", nullable = false)
    private Date dataVigencia;

    @Column(name = "PRECO", nullable = false, scale = 6, precision = 2)
    private Double preco;

    @Column(name = "DESCRICAO", nullable = false, length = 50)
    private String descricao;


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

    public Date getDataVigencia() {
        return dataVigencia;
    }

    public void setDataVigencia(Date dataVigencia) {
        this.dataVigencia = dataVigencia;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
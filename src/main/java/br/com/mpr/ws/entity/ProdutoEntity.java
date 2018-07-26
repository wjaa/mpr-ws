package br.com.mpr.ws.entity;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by wagner on 04/06/18.
 */
@Entity
@Table(name = "PRODUTO")
public class ProdutoEntity implements Serializable {

    private static final long serialVersionUID = -7906534671283904514L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ID_TIPO_PRODUTO", nullable = false)
    @NotNull(message = "Tipo de produto é obrigatório!")
    private Long idTipoProduto;

    @Transient
    private TipoProdutoEntity tipo;

    @NotNull(message = "Descrição do produto é obrigatória!")
    @Column(name = "DESCRICAO", nullable = false, length = 80)
    private String descricao;


    @NotNull(message = "Peso do produto é obrigatória!")
    @Range(min = 0, max = 99999)
    @Column(name = "PESO", nullable = false, scale = 5, precision = 2)
    private Double peso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTipoProduto() {
        return idTipoProduto;
    }

    public void setIdTipoProduto(Long idTipoProduto) {
        this.idTipoProduto = idTipoProduto;
    }

    public TipoProdutoEntity getTipo() {
        return tipo;
    }

    public void setTipo(TipoProdutoEntity tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }
}

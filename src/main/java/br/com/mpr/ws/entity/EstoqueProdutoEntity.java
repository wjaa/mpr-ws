package br.com.mpr.ws.entity;

import javax.persistence.*;

@Entity
@Table(name = "ESTOQUE_PRODUTO")
public class EstoqueProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_ESTOQUE")
    private EstoqueEntity estoque;
    @Column(name = "ID_PRODUTO", nullable = false)
    private Long idProduto;
    @Column(name = "INVALIDO")
    private Boolean invalido;


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

    public Boolean getInvalido() {
        return invalido;
    }

    public void setInvalido(Boolean invalido) {
        this.invalido = invalido;
    }

    public EstoqueEntity getEstoque() {
        return estoque;
    }

    public void setEstoque(EstoqueEntity estoque) {
        this.estoque = estoque;
    }
}

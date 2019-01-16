package br.com.mpr.ws.entity;

import javax.persistence.*;

@Entity
@Table(name = "ESTOQUE_ITEM")
public class EstoqueItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name="ID_ESTOQUE", nullable = false)
    private Long idEstoque;

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


    public Long getIdEstoque() {
        return idEstoque;
    }

    public void setIdEstoque(Long idEstoque) {
        this.idEstoque = idEstoque;
    }

}

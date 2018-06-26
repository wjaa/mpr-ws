package br.com.mpr.ws.entity;

import javax.persistence.*;

/**
 * Created by wagner on 20/06/18.
 */
@Entity
@Table(name = "ITEM_CARRINHO")
public class ItemCarrinhoEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ID_ESTOQUE", nullable = false)
    private Long idEstoque;

    @Column(name = "ID_CARRINHO", nullable = false)
    private Long idCarrinho;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEstoque() {
        return idEstoque;
    }

    public void setIdEstoque(Long idEstoque) {
        this.idEstoque = idEstoque;
    }

    public Long getIdCarrinho() {
        return idCarrinho;
    }

    public void setIdCarrinho(Long idCarrinho) {
        this.idCarrinho = idCarrinho;
    }
}

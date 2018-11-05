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

    @Column(name = "ID_ESTOQUE_ITEM", nullable = false)
    private Long idEstoqueItem;

    @Column(name = "ID_CARRINHO", nullable = false)
    private Long idCarrinho;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCarrinho() {
        return idCarrinho;
    }

    public void setIdCarrinho(Long idCarrinho) {
        this.idCarrinho = idCarrinho;
    }

    public Long getIdEstoqueItem() {
        return idEstoqueItem;
    }

    public void setIdEstoqueItem(Long idEstoqueItem) {
        this.idEstoqueItem = idEstoqueItem;
    }
}

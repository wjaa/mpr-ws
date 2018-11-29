package br.com.mpr.ws.entity;

import br.com.mpr.ws.vo.CarrinhoVo;
import br.com.mpr.ws.vo.ItemCarrinhoVo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "ID_ESTOQUE_ITEM", nullable = false, unique = true)
    private Long idEstoqueItem;

    @Column(name = "ID_CARRINHO", nullable = false)
    private Long idCarrinho;

    @Column(name = "FOTO")
    private String foto;

    @Column(name = "ID_CATALOGO")
    private Long idCatalogo;

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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Long getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(Long idCatalogo) {
        this.idCatalogo = idCatalogo;
    }
}

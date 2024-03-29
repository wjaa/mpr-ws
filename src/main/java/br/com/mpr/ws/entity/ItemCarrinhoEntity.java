package br.com.mpr.ws.entity;

import javax.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "ID_ESTOQUE_ITEM", updatable = false, insertable = false)
    private EstoqueItemEntity estoqueItem;

    @Column(name = "ID_CARRINHO", nullable = false)
    private Long idCarrinho;

    @Column(name = "FOTO_PREVIEW", nullable = false)
    private String fotoPreview;

    @Transient
    private List<ItemCarrinhoAnexoEntity> anexos;

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

    public EstoqueItemEntity getEstoqueItem() {
        return estoqueItem;
    }

    public void setEstoqueItem(EstoqueItemEntity estoqueItem) {
        this.estoqueItem = estoqueItem;
    }

    public List<ItemCarrinhoAnexoEntity> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<ItemCarrinhoAnexoEntity> anexos) {
        this.anexos = anexos;
    }

    public String getFotoPreview() {
        return fotoPreview;
    }

    public void setFotoPreview(String fotoPreview) {
        this.fotoPreview = fotoPreview;
    }
}

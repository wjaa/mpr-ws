package br.com.mpr.ws.entity;

import javax.persistence.*;

/**
 * Created by wagner on 04/02/19.
 */
@Entity
@Table(name = "ITEM_CARRINHO_ANEXO")
public class ItemCarrinhoAnexoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ID_ITEM_CARRINHO", nullable = false)
    private Long idItemCarrinho;

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

    public Long getIdItemCarrinho() {
        return idItemCarrinho;
    }

    public void setIdItemCarrinho(Long idItemCarrinho) {
        this.idItemCarrinho = idItemCarrinho;
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

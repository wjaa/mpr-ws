package br.com.mpr.ws.entity;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "ITEM_PEDIDO_ANEXO")
public class ItemPedidoAnexoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ID_ITEM_PEDIDO", nullable = false)
    private Long idItemPedido;

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

    public Long getIdItemPedido() {
        return idItemPedido;
    }

    public void setIdItemPedido(Long idItemPedido) {
        this.idItemPedido = idItemPedido;
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

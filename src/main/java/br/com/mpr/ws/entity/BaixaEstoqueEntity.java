package br.com.mpr.ws.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wagner on 20/06/18.
 */
@Entity
@Table(name = "BAIXA_ESTOQUE")
public class BaixaEstoqueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ID_ESTOQUE", nullable = false)
    private Long idEstoque;

    @Column(name = "ID_ITEM_PEDIDO", nullable = false)
    private Long idItemPedido;

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

    public Long getIdItemPedido() {
        return idItemPedido;
    }

    public void setIdItemPedido(Long idItemPedido) {
        this.idItemPedido = idItemPedido;
    }
}

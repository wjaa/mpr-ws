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

    @Column(name = "ID_ESTOQUE_PRODUTO", nullable = false)
    private Long idEstoqueProduo;

    @Column(name = "ID_ITEM_PEDIDO", nullable = false)
    private Long idItemPedido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEstoqueProduo() {
        return idEstoqueProduo;
    }

    public void setIdEstoqueProduo(Long idEstoqueProduo) {
        this.idEstoqueProduo = idEstoqueProduo;
    }

    public Long getIdItemPedido() {
        return idItemPedido;
    }

    public void setIdItemPedido(Long idItemPedido) {
        this.idItemPedido = idItemPedido;
    }
}

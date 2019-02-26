package br.com.mpr.ws.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by wagner on 16/02/19.
 */
@Entity
@Table(name = "HISTORICO_PEDIDO")
public class HistoricoPedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ID_PEDIDO", nullable = false)
    private Long idPedido;

    @ManyToOne
    @JoinColumn(name = "ID_STATUS_PEDIDO", nullable = false)
    private StatusPedidoEntity statusPedido;

    @Column(name = "DATA", nullable = false)
    private Date data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public StatusPedidoEntity getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedidoEntity statusPedido) {
        this.statusPedido = statusPedido;
    }
}

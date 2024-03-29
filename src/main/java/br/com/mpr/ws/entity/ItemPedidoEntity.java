package br.com.mpr.ws.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by wagner on 20/06/18.
 */
@Entity
@Table(name = "ITEM_PEDIDO")
public class ItemPedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ID_PEDIDO", nullable = false)
    private Long idPedido;

    @Column(name = "ID_PRODUTO", nullable = false)
    private Long idProduto;

    @Column(name = "VALOR", nullable = false)
    private Double valor;

    /*marca o registro de qual lote ele foi retirado*/
    @Column(name = "ID_ESTOQUE", nullable = false)
    private Long idEstoque;

    @Transient
    private List<ItemPedidoAnexoEntity> anexos;

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

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public List<ItemPedidoAnexoEntity> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<ItemPedidoAnexoEntity> anexos) {
        this.anexos = anexos;
    }

    public Long getIdEstoque() {
        return idEstoque;
    }

    public void setIdEstoque(Long idEstoque) {
        this.idEstoque = idEstoque;
    }
}

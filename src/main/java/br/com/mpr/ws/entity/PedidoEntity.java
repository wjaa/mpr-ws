package br.com.mpr.ws.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by wagner on 04/06/18.
 */
@Entity
@Table(name = "PEDIDO")
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "CODIGO_PEDIDO", length = 16)
    private String codigoPedido;

    @Column(name = "DATA", nullable = false)
    private Date data;

    @Column(name = "ID_CLIENTE", nullable = false)
    private Long idCliente;

    @Column(name = "ID_ENDERECO", nullable = false)
    private Long idEndereco;

    @Transient
    private CupomEntity cupom;

    @Column(name = "ID_CUPOM")
    private Long idCupom;

    @Transient
    private List<ItemPedidoEntity> itens;

    @Column(name = "VALOR_PRODUTOS", nullable = false, scale = 7, precision = 2)
    private Double valorProdutos;

    @Column(name = "VALOR_FRETE", nullable = false, scale = 6, precision = 2)
    private Double valorFrete;

    @Column(name = "VALOR_DESCONTO", nullable = false, scale = 6, precision = 2)
    private Double valorDesconto;

    @Column(name = "VALOR_TOTAL", nullable = false, scale = 7, precision = 2)
    private Double valorTotal;

    @Column(name = "CODIGO_TRANSACAO", length = 64)
    private String codigoTransacao;

    @Column(name = "CODIGO_RASTREIO", length = 64)
    private String codigoRastreio;

    @Column(name = "TIPO_FRETE")
    @Enumerated(EnumType.ORDINAL)
    private FreteType TipoFrete;

    @Column(name = "TIPO_PAGAMENTO")
    @Enumerated(EnumType.ORDINAL)
    private PagamentoType pagamentoType;

    @Column(name = "DATA_ENTREGA", nullable = false)
    private Date dataEntrega;

    @Transient
    private StatusPedidoEntity statusAtual;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(Long idEndereco) {
        this.idEndereco = idEndereco;
    }

    public List<ItemPedidoEntity> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoEntity> itens) {
        this.itens = itens;
    }

    public CupomEntity getCupom() {
        return cupom;
    }

    public void setCupom(CupomEntity cupom) {
        this.cupom = cupom;
    }

    public Long getIdCupom() {
        return idCupom;
    }

    public void setIdCupom(Long idCupom) {
        this.idCupom = idCupom;
    }

    public Double getValorProdutos() {
        return valorProdutos;
    }

    public void setValorProdutos(Double valorProdutos) {
        this.valorProdutos = valorProdutos;
    }

    public Double getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(Double valorFrete) {
        this.valorFrete = valorFrete;
    }

    public Double getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(Double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public String getCodigoTransacao() {
        return codigoTransacao;
    }

    public void setCodigoTransacao(String codigoTransacao) {
        this.codigoTransacao = codigoTransacao;
    }

    public String getCodigoRastreio() {
        return codigoRastreio;
    }

    public void setCodigoRastreio(String codigoRastreio) {
        this.codigoRastreio = codigoRastreio;
    }

    public FreteType getTipoFrete() {
        return TipoFrete;
    }

    public void setTipoFrete(FreteType tipoFrete) {
        TipoFrete = tipoFrete;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public StatusPedidoEntity getStatusAtual() {
        return statusAtual;
    }

    public void setStatusAtual(StatusPedidoEntity statusAtual) {
        this.statusAtual = statusAtual;
    }

    public PagamentoType getPagamentoType() {
        return pagamentoType;
    }

    public void setPagamentoType(PagamentoType pagamentoType) {
        this.pagamentoType = pagamentoType;
    }
}

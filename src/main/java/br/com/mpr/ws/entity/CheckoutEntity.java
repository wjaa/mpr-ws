package br.com.mpr.ws.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by wagner on 11/01/19.
 */
@Entity
@Table(name = "CHECKOUT")
public class CheckoutEntity {

    private Long id;
    private Long idCarrinho;
    private Long idCupom;
    private Double valorProdutos;
    private Double valorFrete;
    private Double valorDesconto;
    private Double valorTotal;
    private Long idEndereco;
    private Integer diasEntrega;
    private FreteType freteType;
    private List<CheckoutFreteEntity> listFrete;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ID_CARRINHO", nullable = false, unique = true)
    public Long getIdCarrinho() {
        return idCarrinho;
    }

    public void setIdCarrinho(Long idCarrinho) {
        this.idCarrinho = idCarrinho;
    }

    @Column(name = "ID_CUPOM")
    public Long getIdCupom() {
        return idCupom;
    }

    public void setIdCupom(Long idCupom) {
        this.idCupom = idCupom;
    }

    @Column(name = "VALOR_PRODUTOS", nullable = false)
    public Double getValorProdutos() {
        return valorProdutos;
    }

    public void setValorProdutos(Double valorProdutos) {
        this.valorProdutos = valorProdutos;
    }

    @Column(name = "VALOR_FRETE", nullable = false)
    public Double getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(Double valorFrete) {
        this.valorFrete = valorFrete;
    }

    @Column(name = "VALOR_DESCONTO", nullable = false)
    public Double getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(Double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    @Column(name = "VALOR_TOTAL", nullable = false)
    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Column(name = "ID_ENDERECO", nullable = false)
    public Long getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(Long idEndereco) {
        this.idEndereco = idEndereco;
    }

    @Column(name = "DIAS_ENTREGA", nullable = false)
    public Integer getDiasEntrega() {
        return diasEntrega;
    }

    public void setDiasEntrega(Integer diasEntrega) {
        this.diasEntrega = diasEntrega;
    }

    @Column(name = "FRETE", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    public FreteType getFreteType() {
        return freteType;
    }

    public void setFreteType(FreteType freteType) {
        this.freteType = freteType;
    }

    @OneToMany(mappedBy = "checkout", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<CheckoutFreteEntity> getListFrete() {
        return listFrete;
    }

    public void setListFrete(List<CheckoutFreteEntity> listFrete) {
        this.listFrete = listFrete;
    }
}

package br.com.mpr.ws.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckoutVo {

    private Long id;
    private EnderecoVo endereco;
    private List<ResultFreteVo> listResultFrete;
    private Double valorProdutos;
    private Double valorDesconto;
    private CarrinhoVo carrinho;
    private Long idCliente;
    private String checkoutToken;
    private CupomVo cupom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnderecoVo getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoVo endereco) {
        this.endereco = endereco;
    }

    public Double getValorProdutos() {
        return valorProdutos != null ? this.valorProdutos : 0.0;
    }

    public void setValorProdutos(Double valorProdutos) {
        this.valorProdutos = valorProdutos;
    }

    public Double getValorTotal() {
        return this.getValorFrete() + this.getValorProdutos() - this.getValorDesconto();
    }

    public Double getValorDesconto() {
        return valorDesconto == null ? 0.0 : valorDesconto;
    }

    public void setValorDesconto(Double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public List<ResultFreteVo> getListResultFrete() {
        return listResultFrete;
    }

    public void setListResultFrete(List<ResultFreteVo> listResultFrete) {
        this.listResultFrete = listResultFrete;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    @JsonProperty
    public Double getValorFrete() {
        for (ResultFreteVo frete : this.listResultFrete){
            if (frete.getSelecionado()){
                return frete.getValor() == null ? 0.0 : frete.getValor();
            }
        }
        return 0.0;
    }

    @JsonProperty
    public Integer getDiasEntrega() {
        for (ResultFreteVo frete : this.listResultFrete){
            if (frete.getSelecionado()){
                return frete.getDiasUteis() == null ? 0 : frete.getDiasUteis();
            }
        }
        return 0;
    }

    @JsonProperty
    public ResultFreteVo getFreteSelecionado() {
        for (ResultFreteVo frete : this.listResultFrete){
            if (frete.getSelecionado()){
                return frete;
            }
        }
        return null;
    }

    public String getCheckoutToken() {
        return checkoutToken;
    }

    public void setCheckoutToken(String checkoutToken) {
        this.checkoutToken = checkoutToken;
    }

    public CarrinhoVo getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(CarrinhoVo carrinho) {
        this.carrinho = carrinho;
    }

    public CupomVo getCupom() {
        return cupom;
    }

    public void setCupom(CupomVo cupom) {
        this.cupom = cupom;
    }
}

package br.com.mpr.ws.vo;


import org.springframework.util.StringUtils;

public class CheckoutForm {

    private Long idCarrinho;
    private Long idCupom;
    private Long idEndereco;
    private Double valorFrete;
    private Double valorTotal;
    private FormaPagamentoVo formaPagamento;

    public Long getIdCarrinho() {
        return idCarrinho;
    }

    public void setIdCarrinho(Long idCarrinho) {
        this.idCarrinho = idCarrinho;
    }

    public FormaPagamentoVo getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamentoVo formaPagamento) {
        this.formaPagamento = formaPagamento;
    }



    public Long getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(Long idEndereco) {
        this.idEndereco = idEndereco;
    }

    private boolean hasCupom(){
        return idCupom != null;
    }

    public Double getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(Double valorFrete) {
        this.valorFrete = valorFrete;
    }

    public Long getIdCupom() {
        return idCupom;
    }

    public void setIdCupom(Long idCupom) {
        this.idCupom = idCupom;
    }
}

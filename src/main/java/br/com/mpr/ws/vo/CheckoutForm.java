package br.com.mpr.ws.vo;


public class CheckoutForm {

    private Long idCheckout;
    private FormaPagamentoVo formaPagamento;

    public Long getIdCheckout() {
        return idCheckout;
    }

    public void setIdCheckout(Long idCheckout) {
        this.idCheckout = idCheckout;
    }

    public FormaPagamentoVo getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamentoVo formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}

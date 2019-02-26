package br.com.mpr.ws.vo;


public class CheckoutForm {

    private Long idCheckout;
    private FormaPagamentoVo formaPagamento;
    private String senderHash;

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

    public String getSenderHash() {
        return senderHash;
    }

    public void setSenderHash(String senderHash) {
        this.senderHash = senderHash;
    }

    @Override
    public String toString() {
        return "CheckoutForm{" +
                "idCheckout=" + idCheckout +
                ", formaPagamento=" + formaPagamento +
                ", senderHash='" + senderHash + '\'' +
                '}';
    }
}

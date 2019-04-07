package br.com.mpr.ws.vo;


import javax.validation.constraints.NotNull;

public class CheckoutForm {

    @NotNull(message = "Id do checkout é obrigatório!")
    private Long idCheckout;

    @NotNull(message = "Forma de pagamento é obrigatória!")
    private FormaPagamentoVo formaPagamento;

    @NotNull(message = "SenderHash é obrigatório!")
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

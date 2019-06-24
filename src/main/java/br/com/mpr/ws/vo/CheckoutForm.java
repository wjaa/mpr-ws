package br.com.mpr.ws.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;

public class CheckoutForm {

    @JsonIgnore
    private Long idCliente;

    @NotNull(message = "Forma de pagamento é obrigatória!")
    private FormaPagamentoVo formaPagamento;

    @NotNull(message = "SenderHash é obrigatório!")
    private String senderHash;

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
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
                "idCliente=" + idCliente +
                ", formaPagamento=" + formaPagamento +
                ", senderHash='" + senderHash + '\'' +
                '}';
    }
}

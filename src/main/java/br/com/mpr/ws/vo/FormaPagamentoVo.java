package br.com.mpr.ws.vo;

public class FormaPagamentoVo {

    public boolean isBoleto() {
        return TipoPagamento.BOLETO.equals(tipoPagamento);
    }
    public boolean isCartaoCredito() {
        return TipoPagamento.CARTAO_CREDITO.equals(tipoPagamento);
    }

    public enum TipoPagamento{
        BOLETO,
        CARTAO_CREDITO
    }


    private TipoPagamento tipoPagamento;
    private CartaoCreditoVo cartaoCredito;

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public CartaoCreditoVo getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(CartaoCreditoVo cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }
}

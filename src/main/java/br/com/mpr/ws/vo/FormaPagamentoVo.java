package br.com.mpr.ws.vo;

import br.com.mpr.ws.entity.PagamentoType;

public class FormaPagamentoVo {

    public boolean isBoleto() {
        return PagamentoType.BOLETO.equals(pagamentoType);
    }
    public boolean isCartaoCredito() {
        return PagamentoType.CARTAO_CREDITO.equals(pagamentoType);
    }

    private PagamentoType pagamentoType;
    private CartaoCreditoVo cartaoCredito;

    public PagamentoType getPagamentoType() {
        return pagamentoType;
    }

    public void setPagamentoType(PagamentoType pagamentoType) {
        this.pagamentoType = pagamentoType;
    }

    public CartaoCreditoVo getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(CartaoCreditoVo cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    @Override
    public String toString() {
        return "FormaPagamentoVo{" +
                "pagamentoType=" + pagamentoType +
                ", cartaoCredito=" + cartaoCredito +
                '}';
    }
}

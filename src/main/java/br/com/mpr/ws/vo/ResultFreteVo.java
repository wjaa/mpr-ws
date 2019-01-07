package br.com.mpr.ws.vo;

import java.util.Date;

/**
 *
 */
public class ResultFreteVo {

    private Date previsaoEntrega;
    private Double valor;


    public Date getPrevisaoEntrega() {
        return previsaoEntrega;
    }

    public void setPrevisaoEntrega(Date previsaoEntrega) {
        this.previsaoEntrega = previsaoEntrega;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}

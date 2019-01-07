package br.com.mpr.ws.vo;

import java.util.Date;

/**
 *
 */
public class ResultFreteVo {

    private Integer diasUteis;
    private Double valor;
    private Date previsaoEntrega;


    public Integer getDiasUteis() {
        return diasUteis;
    }

    public void setDiasUteis(Integer diasUteis) {
        this.diasUteis = diasUteis;
    }

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


    @Override
    public String toString() {
        return "ResultFreteVo{" +
                "diasUteis=" + diasUteis +
                ", valor=" + valor +
                '}';
    }
}

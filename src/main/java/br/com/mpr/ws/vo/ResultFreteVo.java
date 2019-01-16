package br.com.mpr.ws.vo;

import org.springframework.util.StringUtils;

import java.util.Date;

/**
 *
 */
public class ResultFreteVo {

    private Boolean selecionado;
    private Integer diasUteis;
    private Double valor;
    private Date previsaoEntrega;
    private String messageError;


    public Integer getDiasUteis() {
        return diasUteis;
    }

    public ResultFreteVo setDiasUteis(Integer diasUteis) {
        this.diasUteis = diasUteis;
        return this;
    }

    public Date getPrevisaoEntrega() {
        return previsaoEntrega;
    }

    public ResultFreteVo setPrevisaoEntrega(Date previsaoEntrega) {
        this.previsaoEntrega = previsaoEntrega;
        return this;
    }

    public Double getValor() {
        return valor == null ? 0.0 : valor;
    }

    public ResultFreteVo setValor(Double valor) {
        this.valor = valor;
        return this;
    }

    public String getMessageError() {
        return messageError;
    }

    public ResultFreteVo setMessageError(String messageError) {
        this.messageError = messageError;
        return this;
    }

    public boolean hasError(){
        return !StringUtils.isEmpty(this.messageError);
    }


    public Boolean getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Boolean selecionado) {
        this.selecionado = selecionado;
    }

    @Override
    public String toString() {
        return "ResultFreteVo{" +
                "diasUteis=" + diasUteis +
                ", valor=" + valor +
                ", previsaoEntrega=" + previsaoEntrega +
                ", messageError='" + messageError + '\'' +
                '}';
    }
}

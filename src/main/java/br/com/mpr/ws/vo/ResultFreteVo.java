package br.com.mpr.ws.vo;

import br.com.mpr.ws.entity.FreteType;
import br.com.mpr.ws.helper.JacksonDateDeserializer;
import br.com.mpr.ws.helper.JacksonDateSerializer;
import br.com.mpr.ws.utils.DateUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 *
 */
public class ResultFreteVo {

    private Boolean selecionado;
    private Integer diasUteis;
    private Double valor;
    @JsonDeserialize(using = JacksonDateDeserializer.class)
    @JsonSerialize(using = JacksonDateSerializer.class)
    private Date previsaoEntrega;
    private String messageError;
    private FreteType freteType;
    private String obs;


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
                "selecionado=" + selecionado +
                ", diasUteis=" + diasUteis +
                ", valor=" + valor +
                ", previsaoEntrega=" + previsaoEntrega +
                ", messageError='" + messageError + '\'' +
                ", freteType=" + freteType +
                ", obs='" + obs + '\'' +
                '}';
    }

    public FreteType getFreteType() {
        return freteType;
    }

    public void setFreteType(FreteType freteType) {
        this.freteType = freteType;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
}

package br.com.mpr.ws.vo;

/**
 *
 */
public class ResultFreteVo {

    private Integer diasUteis;
    private Double valor;

    public Integer getDiasUteis() {
        return diasUteis;
    }

    public void setDiasUteis(Integer diasUteis) {
        this.diasUteis = diasUteis;
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

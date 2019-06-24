package br.com.mpr.ws.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.util.List;

public class PreviewForm {

    @JsonIgnore
    private Long idCliente;
    @JsonIgnore
    private String sessionToken;

    @NotNull(message = "Produto é obrigatório!")
    private Long idProduto;
    private List<AnexoVo> anexos;

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public List<AnexoVo> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<AnexoVo> anexos) {
        this.anexos = anexos;
    }
}

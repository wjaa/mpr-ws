package br.com.mpr.ws.vo;


import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *
 */
public class ItemCarrinhoForm {

    private Long idCliente;
    private String keyDevice;

    @NotNull(message = "Produto é obrigatório!")
    private Long idProduto;
    private List<AnexoVo> anexos;
    private Long idCarrinho;


    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getKeyDevice() {
        return keyDevice;
    }

    public void setKeyDevice(String keyDevice) {
        this.keyDevice = keyDevice;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public Long getIdCarrinho() {
        return idCarrinho;
    }

    public void setIdCarrinho(Long idCarrinho) {
        this.idCarrinho = idCarrinho;
    }

    public List<AnexoVo> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<AnexoVo> anexos) {
        this.anexos = anexos;
    }
}

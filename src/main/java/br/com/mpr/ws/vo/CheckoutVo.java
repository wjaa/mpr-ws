package br.com.mpr.ws.vo;

import java.util.Date;
import java.util.List;

/**
 *
 */
public class CheckoutVo {

    private List<ProdutoVo> produtos;
    private EnderecoVo enderecoVo;
    private Double valorFrete;
    private Double valorProdutos;
    private Date previsaoEntrega;

    public List<ProdutoVo> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoVo> produtos) {
        this.produtos = produtos;
    }

    public EnderecoVo getEnderecoVo() {
        return enderecoVo;
    }

    public void setEnderecoVo(EnderecoVo enderecoVo) {
        this.enderecoVo = enderecoVo;
    }

    public Double getValorFrete() {
        return valorFrete != null ? this.valorFrete : 0.0;
    }

    public void setValorFrete(Double valorFrete) {
        this.valorFrete = valorFrete;
    }

    public Double getTotal() {
        return this.getValorFrete() + this.getValorProdutos();
    }


    public Double getValorProdutos() {
        return valorProdutos != null ? this.valorProdutos : 0.0;
    }

    public void setValorProdutos(Double valorProdutos) {
        this.valorProdutos = valorProdutos;
    }

    public Date getPrevisaoEntrega() {
        return previsaoEntrega;
    }

    public void setPrevisaoEntrega(Date previsaoEntrega) {
        this.previsaoEntrega = previsaoEntrega;
    }
}

package br.com.mpr.ws.vo;

import java.util.List;

/**
 *
 */
public class CheckoutVo {

    private Long id;
    private List<ProdutoVo> produtos;
    private EnderecoVo endereco;
    private List<ResultFreteVo> listResultFrete;
    private Double valorProdutos;
    private Double valorDesconto;
    private Long idCupom;
    private Long idCarrinho;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProdutoVo> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoVo> produtos) {
        this.produtos = produtos;
    }

    public EnderecoVo getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoVo endereco) {
        this.endereco = endereco;
    }

    public Double getValorProdutos() {
        return valorProdutos != null ? this.valorProdutos : 0.0;
    }

    public void setValorProdutos(Double valorProdutos) {
        this.valorProdutos = valorProdutos;
    }

    public Double getValorTotal() {
        return this.getValorFrete() + this.getValorProdutos() - this.getValorDesconto();
    }

    public Double getValorDesconto() {
        return valorDesconto == null ? 0.0 : valorDesconto;
    }

    public void setValorDesconto(Double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public Long getIdCupom() {
        return idCupom;
    }

    public void setIdCupom(Long idCupom) {
        this.idCupom = idCupom;
    }

    public Long getIdCarrinho() {
        return idCarrinho;
    }

    public void setIdCarrinho(Long idCarrinho) {
        this.idCarrinho = idCarrinho;
    }

    public List<ResultFreteVo> getListResultFrete() {
        return listResultFrete;
    }

    public void setListResultFrete(List<ResultFreteVo> listResultFrete) {
        this.listResultFrete = listResultFrete;
    }

    public Double getValorFrete() {
        for (ResultFreteVo frete : this.listResultFrete){
            if (frete.getSelecionado()){
                return frete.getValor() == null ? 0.0 : frete.getValor();
            }
        }
        return 0.0;
    }

    public Integer getDiasEntrega() {
        for (ResultFreteVo frete : this.listResultFrete){
            if (frete.getSelecionado()){
                return frete.getDiasUteis() == null ? 0 : frete.getDiasUteis();
            }
        }
        return 0;
    }
}

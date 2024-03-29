package br.com.mpr.ws.vo;

import br.com.mpr.ws.entity.ProdutoEntity;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 *
 */
public class ProdutoVo {


    private Long id;

    private String descricao;
    private String descricaoDetalhada;
    private List<String> imagensDestaque;
    private String imgPreview;
    private String imgDestaque;
    private String imgPreviewCliente;
    private Double preco;
    private Double peso;
    private Integer quantidade;
    private String hexaCor;
    private String nomeCor;
    private Double comp;
    private Double larg;
    private Double alt;
    private String referencia;
    private Integer qtdeFotos;
    private List<ProdutoVo> listVariacaoCorProduto;

    public ProdutoVo(){}

    public ProdutoVo(ProdutoEntity produtoEntity) {
        BeanUtils.copyProperties(produtoEntity,this);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricaoDetalhada() {
        return descricaoDetalhada;
    }

    public void setDescricaoDetalhada(String descricaoDetalhada) {
        this.descricaoDetalhada = descricaoDetalhada;
    }

    public List<String> getImagensDestaque() {
        return imagensDestaque;
    }

    public void setImagensDestaque(List<String> imagensDestaque) {
        this.imagensDestaque = imagensDestaque;
    }

    public String getImgDestaque() {
        return imgDestaque;
    }

    public void setImgDestaque(String imgDestaque) {
        this.imgDestaque = imgDestaque;
    }

    public String getImgPreview() {
        return imgPreview;
    }

    public void setImgPreview(String imgPreview) {
        this.imgPreview = imgPreview;
    }

    public String getImgPreviewCliente() {
        return imgPreviewCliente;
    }

    public void setImgPreviewCliente(String imgPreviewCliente) {
        this.imgPreviewCliente = imgPreviewCliente;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getHexaCor() {
        return hexaCor;
    }

    public void setHexaCor(String hexaCor) {
        this.hexaCor = hexaCor;
    }

    public List<ProdutoVo> getListVariacaoCorProduto() {
        return listVariacaoCorProduto;
    }

    public void setListVariacaoCorProduto(List<ProdutoVo> listVariacaoCorProduto) {
        this.listVariacaoCorProduto = listVariacaoCorProduto;
    }

    public String getNomeCor() {
        return nomeCor;
    }

    public void setNomeCor(String nomeCor) {
        this.nomeCor = nomeCor;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getComp() {
        return comp;
    }

    public void setComp(Double comp) {
        this.comp = comp;
    }

    public Double getLarg() {
        return larg;
    }

    public void setLarg(Double larg) {
        this.larg = larg;
    }

    public Double getAlt() {
        return alt;
    }

    public void setAlt(Double alt) {
        this.alt = alt;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Integer getQtdeFotos() {
        return qtdeFotos;
    }

    public void setQtdeFotos(Integer qtdeFotos) {
        this.qtdeFotos = qtdeFotos;
    }
}

package br.com.mpr.ws.vo;

import br.com.mpr.ws.entity.ProdutoEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 *
 */
public class ProdutoVo {


    private Long id;

    private String descricao;
    private String descricaoDetalhada;
    private List<String> listUrlFotoDestaque;
    private String imgPreview;
    private String imgDestaque;
    private String imgSemFoto;
    private Double preco;
    private Integer quantidade;
    private String hexaCor;
    private String nomeCor;
    private List<ProdutoVo> produtosRelacionados;

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

    public List<String> getListUrlFotoDestaque() {
        return listUrlFotoDestaque;
    }

    public void setListUrlFotoDestaque(List<String> listUrlFotoDestaque) {
        this.listUrlFotoDestaque = listUrlFotoDestaque;
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

    public String getImgSemFoto() {
        return imgSemFoto;
    }

    public void setImgSemFoto(String imgSemFoto) {
        this.imgSemFoto = imgSemFoto;
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

    public List<ProdutoVo> getProdutosRelacionados() {
        return produtosRelacionados;
    }

    public void setProdutosRelacionados(List<ProdutoVo> produtosRelacionados) {
        this.produtosRelacionados = produtosRelacionados;
    }

    public String getNomeCor() {
        return nomeCor;
    }

    public void setNomeCor(String nomeCor) {
        this.nomeCor = nomeCor;
    }
}

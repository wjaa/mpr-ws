package br.com.mpr.ws.vo;

import java.util.List;

/**
 *
 */
public class ProdutoVo {


    private Long id;
    private String descricao;
    private String descricaoDetalhada;
    private List<String> listUrlFotoDestaque;
    private String urlFotoPreview;
    private String urlSemFoto;
    private Double preco;


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

    public String getUrlFotoPreview() {
        return urlFotoPreview;
    }

    public void setUrlFotoPreview(String urlFotoPreview) {
        this.urlFotoPreview = urlFotoPreview;
    }

    public String getUrlSemFoto() {
        return urlSemFoto;
    }

    public void setUrlSemFoto(String urlSemFoto) {
        this.urlSemFoto = urlSemFoto;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}

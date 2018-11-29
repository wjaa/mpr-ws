package br.com.mpr.ws.vo;

/**
 *
 */
public class ItemCarrinhoVo {

    private Long id;
    private Long idCarrinho;
    private ProdutoVo produto;
    //TODO teremos a foto com PR ? ou só a foto do cliente ?
    private String urlFoto;
    private Long idCatalogo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCarrinho() {
        return idCarrinho;
    }

    public void setIdCarrinho(Long idCarrinho) {
        this.idCarrinho = idCarrinho;
    }

    public ProdutoVo getProduto() {
        return produto;
    }

    public void setProduto(ProdutoVo produto) {
        this.produto = produto;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public Long getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(Long idCatalogo) {
        this.idCatalogo = idCatalogo;
    }
}

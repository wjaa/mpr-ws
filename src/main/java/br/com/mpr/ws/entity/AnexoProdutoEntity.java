package br.com.mpr.ws.entity;

import javax.persistence.*;

/**
 * Created by wagner on 6/27/18.
 */
@Entity
@Table(name = "ANEXO_PRODUTO")
public class AnexoProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "PATH_HI", nullable = false, length = 200)
    private String pathHi;
    @Column(name = "PATH_PREVIEW", nullable = false, length = 200)
    private String pathThumb;
    @Column(name = "PATH_LOW", nullable = false, length = 200)
    private String pathLow;

    @Column(name = "ID_PRODUTO", nullable = false)
    private Long idProduto;
    @Column(name = "ID_CLIENTE", nullable = false)
    private Long idCliente;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPathHi() {
        return pathHi;
    }

    public void setPathHi(String pathHi) {
        this.pathHi = pathHi;
    }

    public String getPathThumb() {
        return pathThumb;
    }

    public void setPathThumb(String pathThumb) {
        this.pathThumb = pathThumb;
    }

    public String getPathLow() {
        return pathLow;
    }

    public void setPathLow(String pathLow) {
        this.pathLow = pathLow;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }
}

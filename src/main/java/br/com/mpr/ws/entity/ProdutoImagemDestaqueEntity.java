package br.com.mpr.ws.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by wagner on 04/06/18.
 */
@Entity
@Table(name = "PRODUTO_IMAGEM_DESTAQUE")
public class ProdutoImagemDestaqueEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

   /* @Column(name = "ID_PRODUTO", nullable = false)
    private Long idProduto;
*/

    @ManyToOne()
    @JoinColumn(name = "ID_PRODUTO", nullable = false)
    private ProdutoEntity produto;


    @Column(name = "IMG", nullable = false, length = 100)
    private String img;


    @Transient
    private byte [] byteImgDestaque;

    @Transient
    private String nameImgDestaque;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   /* public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }*/

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public byte[] getByteImgDestaque() {
        return byteImgDestaque;
    }

    public void setByteImgDestaque(byte[] byteImgDestaque) {
        this.byteImgDestaque = byteImgDestaque;
    }

    public String getNameImgDestaque() {
        return nameImgDestaque;
    }

    public void setNameImgDestaque(String nameImgDestaque) {
        this.nameImgDestaque = nameImgDestaque;
    }

    public ProdutoEntity getProduto() {
        return produto;
    }

    public void setProduto(ProdutoEntity produto) {
        this.produto = produto;
    }
}

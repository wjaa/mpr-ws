package br.com.mpr.ws.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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

    @JsonIgnore
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


    @JsonIgnore
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoImagemDestaqueEntity that = (ProdutoImagemDestaqueEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nameImgDestaque, that.nameImgDestaque);
    }

    @JsonIgnore
    @Override
    public int hashCode() {
        return Objects.hash(id, nameImgDestaque);
    }

    @JsonIgnore
    @Transient
    public boolean isEmpty() {
        return id == null && img == null && this.byteImgDestaque == null && this.nameImgDestaque == null;
    }
}

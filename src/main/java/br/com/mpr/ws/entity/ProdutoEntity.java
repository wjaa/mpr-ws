package br.com.mpr.ws.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by wagner on 04/06/18.
 */
@Entity
@Table(name = "PRODUTO")
public class ProdutoEntity implements Serializable {

    private static final long serialVersionUID = -7906534671283904514L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ID_TIPO_PRODUTO", nullable = false)
    @NotNull(message = "Tipo de produto é obrigatório!")
    private Long idTipoProduto;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO_PRODUTO", updatable = false, insertable = false)
    private TipoProdutoEntity tipo;

    @NotEmpty(message = "Descrição do produto é obrigatória!")
    @Column(name = "DESCRICAO", nullable = false, length = 80)
    private String descricao;

    @NotEmpty(message = "Referencia do produto é obrigatória!")
    @Column(name = "REFERENCIA", nullable = false, length = 50)
    private String referencia;


    @NotNull(message = "Peso do produto é obrigatória!")
    @Range(min = 0, max = 99999)
    @Column(name = "PESO", nullable = false, scale = 5, precision = 2)
    private Double peso;



    @Column(name = "IMG_DESTAQUE", nullable = false, length = 100)
    private String imgDestaque;

    @Column(name = "IMG_PREVIEW", nullable = false, length = 100)
    private String imgPreview;


    @Length(max = 50, message = "Nome da cor tem um tamanho máximo de 50 caracteres.")
    @Column(name = "NOME_COR", length = 50)
    private String nomeCor;

    @Length(max = 7, message = "Hexa da cor tem tamanho máximo de 7 caracteres")
    @Column(name = "HEXA_COR", length = 7)
    private String hexaCor;

    @Transient
    private byte [] byteImgDestaque;

    @Transient
    private String nameImgDestaque;

    @Transient
    private byte [] byteImgPreview;

    @Transient
    private String nameImgPreview;

    @Transient
    private Double preco;

    @Transient
    private String nomeTipoProduto;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTipoProduto() {
        return idTipoProduto;
    }

    public void setIdTipoProduto(Long idTipoProduto) {
        this.idTipoProduto = idTipoProduto;
    }

    public TipoProdutoEntity getTipo() {
        return tipo;
    }

    public void setTipo(TipoProdutoEntity tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
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

    public byte[] getByteImgDestaque() {
        return byteImgDestaque;
    }

    public void setByteImgDestaque(byte[] byteImgDestaque) {
        this.byteImgDestaque = byteImgDestaque;
    }

    public byte[] getByteImgPreview() {
        return byteImgPreview;
    }

    public void setByteImgPreview(byte[] byteImgPreview) {
        this.byteImgPreview = byteImgPreview;
    }

    public String getNameImgDestaque() {
        return nameImgDestaque;
    }

    public void setNameImgDestaque(String nameImgDestaque) {
        this.nameImgDestaque = nameImgDestaque;
    }

    public String getNameImgPreview() {
        return nameImgPreview;
    }

    public void setNameImgPreview(String nameImgPreview) {
        this.nameImgPreview = nameImgPreview;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getNomeCor() {
        return nomeCor;
    }

    public void setNomeCor(String nomeCor) {
        this.nomeCor = nomeCor;
    }

    public String getHexaCor() {
        return hexaCor;
    }

    public void setHexaCor(String hexaCor) {
        this.hexaCor = hexaCor;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getNomeTipoProduto() {
        return nomeTipoProduto;
    }

    public void setNomeTipoProduto(String nomeTipoProduto) {
        this.nomeTipoProduto = nomeTipoProduto;
    }
}

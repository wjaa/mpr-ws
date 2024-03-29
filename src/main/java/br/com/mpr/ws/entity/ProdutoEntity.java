package br.com.mpr.ws.entity;

import br.com.mpr.ws.vo.ProdutoVo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

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

    @OneToMany(mappedBy = "produto", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProdutoImagemDestaqueEntity> listImgDestaque;

    @NotNull(message = "Peso do produto é obrigatória!")
    @Range(min = 0, max = 99999)
    @Column(name = "PESO", nullable = false, scale = 5, precision = 2)
    private Double peso;

    @Column(name = "IMG_PREVIEW", nullable = false, length = 100)
    private String imgPreview;

    @Column(name = "IMG_PREVIEW_PAISAGEM", length = 100)
    private String imgPreviewPaisagem;

    @Column(name = "IMG_DESTAQUE", nullable = false, length = 100)
    private String imgDestaque;

    @Length(max = 50, message = "Nome da cor tem um tamanho máximo de 50 caracteres.")
    @Column(name = "NOME_COR", length = 50)
    private String nomeCor;

    @Length(max = 7, message = "Hexa da cor tem tamanho máximo de 7 caracteres")
    @Column(name = "HEXA_COR", length = 7)
    private String hexaCor;

    @NotNull(message = "Estoque mínimo é obrigatório!")
    @Range(min = 0, max = 99999)
    @Column(name = "ESTOQUE_MINIMO", nullable = false)
    private Integer estoqueMinimo;


    @NotEmpty(message = "Descrição detalhada do produto é obrigatória!")
    @Column(name = "DESCRICAO_DETALHADA", nullable = false, length = 1000)
    private String descricaoDetalhada;

    @Column(name = "ATIVO", nullable = false)
    private Boolean ativo;

    @Range(min = 0, max = 999)
    @NotNull(message = "Comprimento do produto é obrigatória!")
    @Column(name = "COMP", nullable = false)
    private Double comp;

    @Range(min = 0, max = 999)
    @NotNull(message = "Largura do produto é obrigatória!")
    @Column(name = "LARG", nullable = false)
    private Double larg;

    @Range(min = 0, max = 999)
    @NotNull(message = "Altura do produto é obrigatória!")
    @Column(name = "ALT", nullable = false)
    private Double alt;

    @Column(name = "LANCAMENTO")
    private Boolean lancamento;

    @Column(name = "POPULAR")
    private Boolean popular;

    @Column(name = "DESTAQUE")
    private Boolean destaque;

    @Column(name = "QTDE_FOTOS")
    private Integer qtdeFotos;

    @Transient
    private byte [] byteImgPreview;

    @Transient
    private byte [] byteImgPreviewPaisagem;

    @Transient
    private String nameImgPreview;

    @Transient
    private String nameImgPreviewPaisagem;

    @Transient
    private byte [] byteImgDestaque;

    @Transient
    private String nameImgDestaque;

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

    public String getImgPreview() {
        return imgPreview;
    }

    public void setImgPreview(String imgPreview) {
        this.imgPreview = imgPreview;
    }

    public byte[] getByteImgPreview() {
        return byteImgPreview;
    }

    public void setByteImgPreview(byte[] byteImgPreview) {
        this.byteImgPreview = byteImgPreview;
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

    public Integer getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(Integer estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public List<ProdutoImagemDestaqueEntity> getListImgDestaque() {
        return listImgDestaque;
    }

    public void setListImgDestaque(List<ProdutoImagemDestaqueEntity> listImgDestaque) {
        if (this.listImgDestaque != null && listImgDestaque == null){
            this.listImgDestaque.clear();
        }else{
            this.listImgDestaque = listImgDestaque;
        }
    }

    public String getDescricaoDetalhada() {
        return descricaoDetalhada;
    }

    public void setDescricaoDetalhada(String descricaoDetalhada) {
        this.descricaoDetalhada = descricaoDetalhada;
    }


    public String getImgDestaque() {
        return imgDestaque;
    }

    public void setImgDestaque(String imgDestaque) {
        this.imgDestaque = imgDestaque;
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

    public Boolean getAtivo() {
        return ativo == null ? false : ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public ProdutoVo toVo() {
        return new ProdutoVo(this);
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

    public Boolean getLancamento() {
        return lancamento;
    }

    public void setLancamento(Boolean lancamento) {
        this.lancamento = lancamento;
    }

    public Boolean getPopular() {
        return popular;
    }

    public void setPopular(Boolean popular) {
        this.popular = popular;
    }

    public Boolean getDestaque() {
        return destaque;
    }

    public void setDestaque(Boolean destaque) {
        this.destaque = destaque;
    }

    public Integer getQtdeFotos() {
        return qtdeFotos;
    }

    public void setQtdeFotos(Integer qtdeFotos) {
        this.qtdeFotos = qtdeFotos;
    }

    public String getImgPreviewPaisagem() {
        return imgPreviewPaisagem;
    }

    public void setImgPreviewPaisagem(String imgPreviewPaisagem) {
        this.imgPreviewPaisagem = imgPreviewPaisagem;
    }

    public byte[] getByteImgPreviewPaisagem() {
        return byteImgPreviewPaisagem;
    }

    public void setByteImgPreviewPaisagem(byte[] byteImgPreviewPaisagem) {
        this.byteImgPreviewPaisagem = byteImgPreviewPaisagem;
    }

    public String getNameImgPreviewPaisagem() {
        return nameImgPreviewPaisagem;
    }

    public void setNameImgPreviewPaisagem(String nameImgPreviewPaisagem) {
        this.nameImgPreviewPaisagem = nameImgPreviewPaisagem;
    }
}

package br.com.mpr.ws.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "FRETE_CEP")
public class FreteCepEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "CEP", nullable = false)
    private String cep;

    @Column(name = "TIPO_FRETE", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private FreteType tipoFrete;

    @Column(name = "VALOR", nullable = false)
    private Double valor;

    @Column(name = "DATA_CALCULO", nullable = false)
    private Date dataCalculo;

    @Column(name = "ID_EMBALAGEM", nullable = false)
    private Long idEmbalagem;


    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FreteType getTipoFrete() {
        return tipoFrete;
    }

    public void setTipoFrete(FreteType tipoFrete) {
        this.tipoFrete = tipoFrete;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getDataCalculo() {
        return dataCalculo;
    }

    public void setDataCalculo(Date dataCalculo) {
        this.dataCalculo = dataCalculo;
    }

    public Long getIdEmbalagem() {
        return idEmbalagem;
    }

    public void setIdEmbalagem(Long idEmbalagem) {
        this.idEmbalagem = idEmbalagem;
    }
}

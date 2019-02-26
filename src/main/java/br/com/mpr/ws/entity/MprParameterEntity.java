package br.com.mpr.ws.entity;

import br.com.mpr.ws.service.MprParameterService;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wagner on 25/02/19.
 */
@Entity
@Table(name = "MPR_PARAMETER")
public class MprParameterEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "CHAVE", nullable = false)
    @Enumerated(EnumType.STRING)
    private MprParameterType chave;

    @Column(name = "VALOR", nullable = false)
    private String valor;

    @Column(name = "DATA_ATUALIZACAO", nullable = false)
    private Date dataAtualizacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public MprParameterType getChave() {
        return chave;
    }

    public void setChave(MprParameterType chave) {
        this.chave = chave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}

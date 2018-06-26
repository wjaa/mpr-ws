package br.com.mpr.ws.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by wagner on 20/06/18.
 */
public class CarrinhoEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Transient
    private List<ItemCarrinhoEntity> itens;

    @Column(name = "ID_CLIENTE")
    private Long idCliente;

    //chave do celular do cliente quando ele nao tiver login.
    @Column(name = "KEY_DEVICE", length = 255)
    private String keyDevice ;

    @Column(name = "DATA_CRIACAO", nullable = false)
    private Date dataCriacao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemCarrinhoEntity> getItens() {
        return itens;
    }

    public void setItens(List<ItemCarrinhoEntity> itens) {
        this.itens = itens;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getKeyDevice() {
        return keyDevice;
    }

    public void setKeyDevice(String keyDevice) {
        this.keyDevice = keyDevice;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}

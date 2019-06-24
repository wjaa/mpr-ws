package br.com.mpr.ws.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by wagner on 20/06/18.
 */
@Entity
@Table(name = "CARRINHO")
public class CarrinhoEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Transient
    private List<ItemCarrinhoEntity> itens;

    @Column(name = "ID_CLIENTE", unique = true)
    private Long idCliente;

    //session do cliente quando ele nao estiver logado.
    @Column(name = "ID_SESSION", unique = true)
    private Long idSession;

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

    public Long getIdSession() {
        return idSession;
    }

    public void setIdSession(Long idSession) {
        this.idSession = idSession;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}

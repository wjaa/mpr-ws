package br.com.mpr.ws.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wagner on 16/02/19.
 */
@Entity
@Table(name = "STATUS_PEDIDO")
public class StatusPedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "NOME_CLIENTE", nullable = false)
    private String nomeCliente;

    @Column(name = "SYS_CODE", length = 4)
    @Enumerated(EnumType.STRING)
    private SysCodeType syscode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public SysCodeType getSyscode() {
        return syscode;
    }

    public void setSyscode(SysCodeType syscode) {
        this.syscode = syscode;
    }
}

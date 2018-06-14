package br.com.mpr.ws.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by wagner on 04/06/18.
 */
@Entity
@Table(name = "CLIENTE")
public class ClienteEntity {

    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private List<EnderecoEntity> enderecos;
    private String celular;
    private Date aniversario;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NOME", nullable = false, length = 60)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name = "EMAIL", nullable = false, length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "CPF", nullable = false, length = 11)
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Transient
    public List<EnderecoEntity> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoEntity> enderecos) {
        this.enderecos = enderecos;
    }

    @Column(name = "CELULAR", nullable = false, length = 13)
    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    @Column(name = "ANIVERSARIO")
    public Date getAniversario() {
        return aniversario;
    }

    public void setAniversario(Date aniversario) {
        this.aniversario = aniversario;
    }
}

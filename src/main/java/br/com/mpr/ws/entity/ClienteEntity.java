package br.com.mpr.ws.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by wagner on 04/06/18.
 */
@Entity
@Table(name = "CLIENTE")
public class ClienteEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NOME", nullable = false, length = 60)
    @NotNull(message = "Nome é obrigatório!")
    private String nome;

    @Column(name = "EMAIL", nullable = false, length = 100)
    @NotNull(message = "Email é obrigatório!")
    private String email;

    @Column(name = "CPF", nullable = false, length = 11)
    @NotNull(message = "Cpf é obrigatório!")
    private String cpf;

    @Transient
    private List<EnderecoEntity> enderecos;

    @Column(name = "CELULAR", nullable = false, length = 13)
    @NotNull(message = "Celular é obrigatório!")
    private String celular;

    @Column(name = "ANIVERSARIO")
    private Date aniversario;

    @Column(name = "KEY_DEVICE", length = 255)
    private String keyDevice ;

    @Column(name = "ATIVO", nullable = false)
    private Boolean ativo;

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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    public List<EnderecoEntity> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoEntity> enderecos) {
        this.enderecos = enderecos;
    }


    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }


    public Date getAniversario() {
        return aniversario;
    }

    public void setAniversario(Date aniversario) {
        this.aniversario = aniversario;
    }

    public String getKeyDevice() {
        return keyDevice;
    }

    public void setKeyDevice(String keyDevice) {
        this.keyDevice = keyDevice;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}

package br.com.mpr.ws.entity;

import br.com.mpr.ws.constants.GeneroType;
import br.com.mpr.ws.helper.JacksonDateSerializer;
import br.com.mpr.ws.utils.NumberUtils;
import br.com.mpr.ws.utils.StringUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @NotNull(message = "E-mail é obrigatório!")
    @Email(message = "E-mail inválido!")
    private String email;

    @Column(name = "CPF", nullable = false, length = 11)
    @NotNull(message = "Cpf é obrigatório!")
    private String cpf;

    @Transient
    @Valid
    private List<EnderecoEntity> enderecos;

    @Column(name = "CELULAR", nullable = false, length = 13)
    @NotNull(message = "Celular é obrigatório!")
    private String celular;

    @Column(name = "ANIVERSARIO")
    @JsonSerialize(using = JacksonDateSerializer.class)
    private Date aniversario;

    @Column(name = "GENERO", length = 1)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Genero é obrigatório!")
    private GeneroType genero ;

    @Column(name = "ATIVO", nullable = false)
    private Boolean ativo;

    @ManyToOne
    @JoinColumn(name = "ID_LOGIN")
    private LoginEntity login;

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
        this.cpf = String.valueOf(StringUtils.getNumber(cpf));
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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public GeneroType getGenero() {
        return genero;
    }

    public void setGenero(GeneroType genero) {
        this.genero = genero;
    }

    public LoginEntity getLogin() {
        return login;
    }

    public void setLogin(LoginEntity login) {
        this.login = login;
    }
}

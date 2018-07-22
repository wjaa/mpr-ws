package br.com.mpr.ws.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by wagner on 04/06/18.
 */
@Entity
@Table(name = "FORNECEDOR")
public class FornecedorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NOME", nullable = false, length = 60)
    @NotNull(message = "Nome do fornecedor não pode ser vazio.")
    private String nome;

    @Column(name = "EMAIL", nullable = false, length = 100)
    @Email
    @NotNull(message = "Email do fornecedor não pode ser vazio.")
    private String email;

    @Column(name = "CNPJ", length = 14)
    private String cnpj;

    @Column(name = "ENDERECO", nullable = false, length = 200)
    @NotNull(message = "Endereço do fornecedor não pode ser vazio.")
    private String endereco;

    @Column(name = "TELEFONE_PRINCIPAL", nullable = false, length = 13)
    @NotNull(message = "Telefone principal do fornecedor não pode ser vazio.")
    private String telefonePrincipal;

    @Column(name = "TELEFONE_SECUNDARIO", length = 13)
    private String telefoneSecundario;

    @Column(name = "ATIVO")
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefonePrincipal() {
        return telefonePrincipal;
    }

    public void setTelefonePrincipal(String telefonePrincipal) {
        this.telefonePrincipal = telefonePrincipal;
    }

    public String getTelefoneSecundario() {
        return telefoneSecundario;
    }

    public void setTelefoneSecundario(String telefoneSecundario) {
        this.telefoneSecundario = telefoneSecundario;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}

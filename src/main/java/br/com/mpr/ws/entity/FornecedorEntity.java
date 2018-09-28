package br.com.mpr.ws.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by wagner on 04/06/18.
 */
@Entity
@Table(name = "FORNECEDOR")
public class FornecedorEntity implements Serializable {

    private static final long serialVersionUID = -8102000405120233227L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NOME", nullable = false, length = 60)
    @NotEmpty(message = "Nome do fornecedor não pode ser vazio.")
    @Size(max = 60, message = "Nome só permite 60 caracteres")
    private String nome;

    @Column(name = "EMAIL", nullable = false, length = 100)
    @Email
    @NotEmpty(message = "Email do fornecedor não pode ser vazio.")
    @Size(max = 100, message = "Email só permite 100 caracteres")
    private String email;

    @Column(name = "CNPJ", length = 14)
    @Size(max = 14, message = "Cnpj inválido")
    private String cnpj;

    @Column(name = "ENDERECO", nullable = false, length = 200)
    @NotEmpty(message = "Endereço do fornecedor não pode ser vazio.")
    @Size(max = 200, message = "Endereço só permite 200 caracteres")
    private String endereco;

    @Column(name = "TELEFONE_PRINCIPAL", nullable = false, length = 13)
    @NotEmpty(message = "Telefone principal do fornecedor não pode ser vazio.")
    @Size(max = 13, message = "Telefone só permite 13 caracteres")
    private String telefonePrincipal;

    @Column(name = "TELEFONE_SECUNDARIO", length = 13)
    @Size(max = 13, message = "Telefone só permite 13 caracteres")
    private String telefoneSecundario;

    @Column(name = "ATIVO")
    private Boolean ativo;

    @Column(name = "OBSERVACAO", length = 255)
    private String observacao;

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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}

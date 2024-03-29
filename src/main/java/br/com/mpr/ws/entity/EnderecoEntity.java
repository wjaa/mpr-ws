package br.com.mpr.ws.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * Created by wagner on 04/06/18.
 */
@Entity
@Table(name = "ENDERECO")
@Valid
public class EnderecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "CEP", nullable = false, length = 8)
    @NotEmpty(message = "Cep é obrigatório!")
    private String cep;

    @Column(name = "LOGRADOURO", nullable = false, length = 80)
    @NotEmpty(message = "Logradouro é obrigatório!")
    private String logradouro;

    @Column(name = "NUMERO", length = 10, nullable = false)
    @NotEmpty(message = "Número é obrigatório!")
    private String numero;

    @Column(name = "COMPLEMENTO", length = 60)
    private String complemento;

    @Column(name = "BAIRRO", length = 60, nullable = false)
    @NotEmpty(message = "Bairro é obrigatório!")
    private String bairro;

    @Column(name = "CIDADE", length = 60, nullable = false)
    @NotEmpty(message = "Cidade é obrigatório!")
    private String cidade;

    @Column(name = "UF", length = 2, nullable = false)
    @NotEmpty(message = "UF é obrigatório!")
    private String uf;

    @Column(name = "ID_CLIENTE", nullable = false)
    private Long idCliente;

    @Column(name = "ATIVO", nullable = false)
    private Boolean ativo;

    @NotEmpty(message = "Descrição do endereço é obrigatório!")
    @Column(name = "DESCRICAO", nullable = false, length = 50)
    private String descricao;

    @Column(name = "OBSERVACAO", length = 150)
    private String observacao;

    @Column(name = "PRINCIPAL", nullable = false)
    private Boolean principal;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    @Transient
    @JsonIgnore
    public String getEnderecoFull() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.logradouro)
                .append(this.numero != null ? ", " + this.numero : "")
                .append(this.bairro != null ? " - " + this.bairro : "")
                .append(this.cidade != null ? " - " + this.cidade : "")
                .append(this.uf != null ? " - " + this.uf : "");
        return sb.toString() ;
    }

    @Transient
    @JsonIgnore
    public String getEnderecoPart1() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.logradouro)
                .append(this.numero != null ? ", " + this.numero : "")
                .append(this.complemento != null ? " - " + this.complemento : "");
        return sb.toString() ;
    }

    @Transient
    @JsonIgnore
    public String getEnderecoPart2() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.cep)
                .append(this.bairro != null ? " - " + this.bairro : "")
                .append(this.cidade != null ? " - " + this.cidade : "")
                .append(this.uf != null ? " - " + this.uf : "");
        return sb.toString() ;
    }
}

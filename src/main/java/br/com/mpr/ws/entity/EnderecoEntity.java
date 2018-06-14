package br.com.mpr.ws.entity;

import javax.persistence.*;

/**
 * Created by wagner on 04/06/18.
 */
@Entity
@Table(name = "ENDERECO")
public class EnderecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "CEP", nullable = false, length = 8)
    private String cep;

    @Column(name = "LOGRADOURO", nullable = false, length = 80)
    private String logradouro;

    @Column(name = "LOGRADOURO", length = 10)
    private String numero;

    @Column(name = "COMPLEMENTO", length = 50)
    private String complemento;

    @Column(name = "COMPLEMENTO", length = 60, nullable = false)
    private String bairro;

    @Column(name = "COMPLEMENTO", length = 40, nullable = false)
    private String cidade;

    @Column(name = "COMPLEMENTO", length = 2, nullable = false)
    private String uf;


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
}

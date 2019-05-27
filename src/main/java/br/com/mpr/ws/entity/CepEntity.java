package br.com.mpr.ws.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CEP")
public class CepEntity {

    @Id
    private String cep;

    @Column(name = "LOGRADOURO", length = 100)
    private String logradouro;

    @Column(name = "BAIRRO", length = 100)
    private String bairro;

    @Column(name = "CIDADE", length = 60)
    private String cidade;

    @Column(name = "UF", length = 2)
    private String uf;

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

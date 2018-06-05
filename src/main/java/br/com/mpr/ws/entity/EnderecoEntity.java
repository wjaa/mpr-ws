package br.com.mpr.ws.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by wagner on 04/06/18.
 */
@Entity
@Table(name = "ENDERECO")
public class EnderecoEntity {

    private Long id;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;



}

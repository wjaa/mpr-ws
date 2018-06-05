package br.com.mpr.ws.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
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



}

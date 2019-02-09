package br.com.mpr.ws.vo;

import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.entity.EnderecoEntity;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wagner on 07/02/19.
 */
public class ClienteVo {

    private Long id;
    private String nome;
    private String email;
    private List<EnderecoVo> enderecos;

    public ClienteVo(){}

    public ClienteVo(ClienteEntity cliente){
        BeanUtils.copyProperties(cliente,this, "enderecos");
        this.setEnderecos(new ArrayList<>());
        for (EnderecoEntity e : cliente.getEnderecos()){
            this.getEnderecos().add(EnderecoVo.toVo(e));
        }

    }

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

    public List<EnderecoVo> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoVo> enderecos) {
        this.enderecos = enderecos;
    }
}

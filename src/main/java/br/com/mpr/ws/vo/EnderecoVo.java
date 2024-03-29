package br.com.mpr.ws.vo;

import br.com.mpr.ws.entity.EnderecoEntity;

/**
 *
 */
public class EnderecoVo {

    private Long id;
    private String descricao;
    private String endereco;
    private String cep;

    public static EnderecoVo toVo(EnderecoEntity endereco) {
        EnderecoVo enderecoVo = new EnderecoVo();
        enderecoVo.setDescricao(endereco.getDescricao());
        enderecoVo.setEndereco(endereco.getEnderecoFull());
        enderecoVo.setId(endereco.getId());
        enderecoVo.setCep(endereco.getCep());
        return enderecoVo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}

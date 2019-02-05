package br.com.mpr.ws.vo;

import br.com.mpr.ws.entity.CupomEntity;
import org.springframework.beans.BeanUtils;

/**
 * Created by wagner on 02/02/19.
 */
public class CupomVo {
    private Long id;
    private String hash;
    private Double porcentagem;

    public CupomVo(){}

    public CupomVo(CupomEntity cupomEntity){
        BeanUtils.copyProperties(cupomEntity,this);

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Double getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(Double porcentagem) {
        this.porcentagem = porcentagem;
    }
}

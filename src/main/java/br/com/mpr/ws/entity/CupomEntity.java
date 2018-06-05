package br.com.mpr.ws.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by wagner on 04/06/18.
 */
@Entity
@Table(name = "CUPOM_DESCONTO")
public class CupomEntity {

    private Long id;
    private String descricao;
    private String hash;
    private Date dataInicio;
    private Date dataFim;
    private Boolean promocao;
    //se nao for promocao essa quantidade tem que ter no maximo 1x
    private Integer qtdeUtilizada;
    private Double porcentagem;
}

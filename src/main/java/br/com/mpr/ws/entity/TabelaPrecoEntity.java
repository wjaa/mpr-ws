package br.com.mpr.ws.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by wagner on 04/06/18.
 */
@Entity
@Table(name = "TABELA_PRECO")
public class TabelaPrecoEntity {

    private Long id;
    private Double preco;
    private Date dataVigencia;
    private String descricao;


}
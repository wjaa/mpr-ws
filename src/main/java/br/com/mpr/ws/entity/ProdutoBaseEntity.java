package br.com.mpr.ws.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by wagner on 04/06/18.
 */
@Entity
@Table(name = "PRODUTO_BASE")
public class ProdutoBaseEntity {

    private Long id;
    private TipoProdutoEntity tipo;
    private String descricao;



}

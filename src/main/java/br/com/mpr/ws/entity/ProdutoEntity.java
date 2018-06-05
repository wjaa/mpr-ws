package br.com.mpr.ws.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by wagner on 04/06/18.
 */
@Entity
@Table(name = "PRODUTO")
public class ProdutoEntity {

    private Long id;
    private ProdutoBaseEntity produtoBase;
    private Double valor;






}



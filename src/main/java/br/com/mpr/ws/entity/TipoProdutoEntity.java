package br.com.mpr.ws.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Created by wagner on 04/06/18.
 */
@Entity
@Table(name = "TIPO_PRODUTO")
public class TipoProdutoEntity implements Serializable {

    private static final long serialVersionUID = -1975242527583241235L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @NotEmpty(message = "Descrição do tipo de produto não pode ser vazia.")
    @Column(name = "DESCRICAO", nullable = false, length = 80)
    private String descricao;

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
}

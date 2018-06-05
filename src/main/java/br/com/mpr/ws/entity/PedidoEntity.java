package br.com.mpr.ws.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Created by wagner on 04/06/18.
 */
@Entity
@Table(name = "PEDIDO")
public class PedidoEntity {


    private Long id;
    private Date data;
    private Integer idCliente;
    //o endereco fica no pedido, porque o cliente pode ter mais de um endereço cadastrado. Por exemplo
    //ele tenha enviado um PR para algum presenteado.
    private Integer idEndereco;
    private List<ProdutoEntity> produtos;
    private CupomEntity cupom;
    private Double valor;




}

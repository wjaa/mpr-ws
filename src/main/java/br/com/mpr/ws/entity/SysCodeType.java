package br.com.mpr.ws.entity;

/**
 * Created by wagner on 16/02/19.
 */
public enum SysCodeType {

    PECR("Pedido criado"),
    AGPG("Aguardando pagto."),
    PGCF("Pagto. confirmado"),
    CFPE("Em confeccção"),
    CNFC("Confeccionado"),
    PEEB("Embalado"),
    PEDP("Despachado"),
    ETRS("Em transito"),
    ETRG("Entregue"),
    CACL("Cancelado"),
    RCSD("Recusado"),
    PGNC("Pagto não confirmado"),
    DVLC("Devolução"),
    ERRO("Erro no pedido"),

    ;

    SysCodeType(String desc){
        this.desc = desc;
    }

    private String desc;

    public String getDesc() {
        return desc;
    }
}

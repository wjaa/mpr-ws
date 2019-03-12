package br.com.mpr.ws.vo;

import br.com.mpr.ws.entity.SysCodeType;

/**
 *
 */
public class PedidoFindForm {

    private String codigo;
    private Long idCliente;
    private SysCodeType sysCode;

    public PedidoFindForm(){}

    public PedidoFindForm(SysCodeType sysCode) {
        this.sysCode = sysCode;
    }

    public PedidoFindForm(String codigo) {
        this.codigo = codigo;
    }

    public PedidoFindForm(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public SysCodeType getSysCode() {
        return sysCode;
    }

    public void setSysCode(SysCodeType sysCode) {
        this.sysCode = sysCode;
    }
}




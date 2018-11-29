package br.com.mpr.ws.vo;

import br.com.mpr.ws.entity.CarrinhoEntity;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 *
 */
public class CarrinhoVo {

    private Long idCarrinho;
    private Long idCliente;
    private String keyDevice;
    private List<ItemCarrinhoVo> items;

    public static CarrinhoVo toVo(CarrinhoEntity carrinhoEntity) {
        CarrinhoVo vo = new CarrinhoVo();

        BeanUtils.copyProperties(carrinhoEntity, vo);
        vo.setIdCarrinho(carrinhoEntity.getId());
        return vo;
    }

    public Long getIdCarrinho() {
        return idCarrinho;
    }

    public void setIdCarrinho(Long idCarrinho) {
        this.idCarrinho = idCarrinho;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getKeyDevice() {
        return keyDevice;
    }

    public void setKeyDevice(String keyDevice) {
        this.keyDevice = keyDevice;
    }

    public List<ItemCarrinhoVo> getItems() {
        return items;
    }

    public void setItems(List<ItemCarrinhoVo> items) {
        this.items = items;
    }
}

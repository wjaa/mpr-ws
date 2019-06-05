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
    private ResultFreteVo resultFrete;

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

    public ResultFreteVo getResultFrete() {
        return resultFrete;
    }

    public void setResultFrete(ResultFreteVo resultFrete) {
        this.resultFrete = resultFrete;
    }

    public Integer getTotalItens(){
        return this.items != null ? this.items.size() : 0;
    }

    public Double getValorFrete(){
        return this.resultFrete == null ? 0.0 : this.resultFrete.getValor();
    }

    public Double getValorItens(){
        Double total = 0.0;

        if (items != null){
            for(ItemCarrinhoVo vo : items){
                total += vo.getProduto().getPreco();
            }
        }

        return total;
    }

    public Double getValorTotalCarrinho(){
        return getValorItens() + getValorFrete();
    }
}

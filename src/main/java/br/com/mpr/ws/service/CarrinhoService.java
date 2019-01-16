package br.com.mpr.ws.service;


import br.com.mpr.ws.exception.CarrinhoServiceException;
import br.com.mpr.ws.vo.CarrinhoVo;
import br.com.mpr.ws.vo.ItemCarrinhoForm;

/**
 *
 */
public interface CarrinhoService {

    CarrinhoVo addCarrinho(ItemCarrinhoForm item) throws CarrinhoServiceException;

    CarrinhoVo getCarrinhoByIdCliente(Long idCliente);

    CarrinhoVo getCarrinhoByKeyDevice(String keyDevice);

    CarrinhoVo removeItem(Long idItem) throws CarrinhoServiceException;

    CarrinhoVo getCarrinhoById(Long idCarrinho) throws CarrinhoServiceException;
}

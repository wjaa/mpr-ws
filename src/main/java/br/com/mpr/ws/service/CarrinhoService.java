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

    CarrinhoVo getCarrinhoBySessionToken(String sessionToken);

    CarrinhoVo removeItem(Long idItem, Long idCliente) throws CarrinhoServiceException;

    CarrinhoVo removeItem(Long idItem, String sessionToken) throws CarrinhoServiceException;

    CarrinhoVo getCarrinhoById(Long idCarrinho) throws CarrinhoServiceException;
}

package br.com.mpr.ws.service;

import br.com.mpr.ws.exception.CarrinhoServiceException;
import br.com.mpr.ws.vo.CarrinhoVo;
import br.com.mpr.ws.vo.ItemCarrinhoForm;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class CarrinhoServiceImpl implements CarrinhoService {
    @Override
    public CarrinhoVo addCarrinho(ItemCarrinhoForm item) throws CarrinhoServiceException {
        return null;
    }

    @Override
    public CarrinhoVo getCarrinho(Long idCliente, String keyDevice) {
        return null;
    }

    @Override
    public CarrinhoVo removeItem(Long idItem) throws CarrinhoServiceException {
        return null;
    }
}

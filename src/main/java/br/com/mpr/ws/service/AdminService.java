package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.FornecedorEntity;
import br.com.mpr.ws.entity.TabelaPrecoEntity;
import br.com.mpr.ws.exception.AdminServiceException;

/**
 * Created by wagner on 6/25/18.
 */
public interface AdminService {


    FornecedorEntity saveFornecedor(FornecedorEntity fe) throws AdminServiceException;


    TabelaPrecoEntity savePreco(TabelaPrecoEntity preco) throws AdminServiceException;
}

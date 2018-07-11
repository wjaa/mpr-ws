package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.entity.FornecedorEntity;
import br.com.mpr.ws.entity.TabelaPrecoEntity;
import br.com.mpr.ws.exception.AdminServiceException;

import java.util.List;

/**
 * Created by wagner on 6/25/18.
 */
public interface AdminService {


    FornecedorEntity getFornecedorById(long id);

    List<FornecedorEntity> listAllFornecedor();

    FornecedorEntity saveFornecedor(FornecedorEntity fe) throws AdminServiceException;

    void removeFornecedorById(long id) throws AdminServiceException;

    TabelaPrecoEntity savePreco(TabelaPrecoEntity preco) throws AdminServiceException;

    void removeTabelaPrecoById(long id) throws AdminServiceException;


}

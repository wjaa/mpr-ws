package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.entity.FornecedorEntity;
import br.com.mpr.ws.entity.TabelaPrecoEntity;
import br.com.mpr.ws.exception.AdminServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wagner on 6/25/18.
 */
@Service
public class AdminServiceImpl implements AdminService {


    @Autowired
    private CommonDao commonDao;

    @Override
    public FornecedorEntity saveFornecedor(FornecedorEntity fe) throws AdminServiceException {

        if (fe.getId() == null){
            fe = commonDao.save(fe);
        }else{
            FornecedorEntity fornecedorMerge = commonDao.get(FornecedorEntity.class, fe.getId());
            BeanUtils.copyProperties(fe,fornecedorMerge);
            commonDao.update(fe);
        }
        return fe;
    }

    @Override
    public TabelaPrecoEntity savePreco(TabelaPrecoEntity tabPreco) throws AdminServiceException {


        List<TabelaPrecoEntity> resultList = commonDao.findByProperties(TabelaPrecoEntity.class,
                new String[]{"idProduto","dataVigencia"},
                new Object[]{tabPreco.getIdProduto(),tabPreco.getDataVigencia()});

        if (resultList.size() > 0){
            AdminServiceException exception =
                    new AdminServiceException("Já existe um preço cadastrado para esse produto com essa data de vigência.");

            if (tabPreco.getId() == null){
                throw exception;
            }

            //se nao for o mesmo ID o usuario está tentando inserir um novo preco com a mesma data de vigencia.
            if (tabPreco.getId() != null && !resultList.get(0).getId().equals(tabPreco.getId())){
                throw exception;
            }

        }

        if (tabPreco.getId() == null){
            tabPreco = commonDao.save(tabPreco);
        }else{
            TabelaPrecoEntity tabMerged = commonDao.get(TabelaPrecoEntity.class, tabPreco.getId());
            BeanUtils.copyProperties(tabPreco,tabMerged);
            commonDao.update(tabMerged);
        }


        return tabPreco;
    }

    @Override
    public ClienteEntity saveCliente(ClienteEntity cliente) throws AdminServiceException {
        if (cliente.getId() == null){
            cliente = commonDao.save(cliente);
        }else{
            FornecedorEntity fornecedorMerge = commonDao.get(FornecedorEntity.class, cliente.getId());
            BeanUtils.copyProperties(cliente,fornecedorMerge);
            commonDao.update(cliente);
        }
        return cliente;
    }
}

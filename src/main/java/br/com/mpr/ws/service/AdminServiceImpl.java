package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.*;
import br.com.mpr.ws.exception.AdminServiceException;
import br.com.mpr.ws.utils.DateUtils;
import br.com.mpr.ws.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by wagner on 6/25/18.
 */
@Service
public class AdminServiceImpl implements AdminService {


    @Autowired
    private CommonDao commonDao;

    @Override
    public FornecedorEntity getFornecedorById(long id) {
        return commonDao.findByPropertiesSingleResult(FornecedorEntity.class,
                new String[]{"id","ativo"},
                new Object[]{id,true});
    }

    @Override
    public List<FornecedorEntity> listAllFornecedor() {
        return commonDao.findByProperties(FornecedorEntity.class,
                new String[]{"ativo"},
                new Object[]{true});
    }

    @Override
    public FornecedorEntity saveFornecedor(FornecedorEntity fe) throws AdminServiceException {

        if (fe.getId() == null){
            List<FornecedorEntity> listFornec = commonDao.findByProperties(FornecedorEntity.class,
                    new String[]{"cnpj"}, new Object[]{fe.getCnpj()});

            if (listFornec.size() > 0){
                throw new AdminServiceException("Já existe um fornecedor cadastrado com esse cnpj!");
            }

            fe.setAtivo(true);
            fe = commonDao.save(fe);
        }else{
            FornecedorEntity fornecedorMerge = commonDao.get(FornecedorEntity.class, fe.getId());
            BeanUtils.copyProperties(fe,fornecedorMerge);
            commonDao.update(fe);
        }
        return fe;
    }


    @Override
    public void removeFornecedorById(long id) throws AdminServiceException {

        FornecedorEntity entity = commonDao.get(FornecedorEntity.class, id);
        if (entity == null){
            throw new AdminServiceException("Fornecedor não existe com id = #" +id);
        }

        entity.setAtivo(false);

        commonDao.update(entity);
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

        if (DateUtils.isLesserEqual(tabPreco.getDataVigencia(), new Date())) {
            throw new AdminServiceException("Você não pode " + tabPreco.getId() == null ? "criar" : "alterar" +
                    " uma tabela de preço retroativa!");
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
    public void removeTabelaPrecoById(long id) throws AdminServiceException {
        TabelaPrecoEntity tabelaPrecoEntity = commonDao.get(TabelaPrecoEntity.class, id);
        if (tabelaPrecoEntity == null){
            throw new AdminServiceException("Tabela de preço não existe com id = #" + id);
        }

        if (DateUtils.isLesserEqual(tabelaPrecoEntity.getDataVigencia(), new Date())) {
            throw new AdminServiceException("Tabela de preço retroativa, não pode ser removida!");
        }
        commonDao.remove(TabelaPrecoEntity.class,id);

    }




    //TODO NAO EXISTE REMOVER REGISTROS E SIM DESATIVAR.
}

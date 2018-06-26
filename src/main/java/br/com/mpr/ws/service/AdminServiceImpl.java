package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.FornecedorEntity;
import br.com.mpr.ws.exception.AdminServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

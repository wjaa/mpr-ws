package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.vo.ProdutoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
public class ProdutoServiceImpl implements ProdutoService {


    @Resource(name = "ProdutoService.findAllProduto")
    private String findAllProduto;


    @Autowired
    private CommonDao commonDao;

    @Override
    public List<ProdutoVo> listAll() {

        //commonDao.fin

        return null;
    }
}

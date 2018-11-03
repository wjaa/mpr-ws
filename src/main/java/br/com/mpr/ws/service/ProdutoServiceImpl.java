package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.ProdutoEntity;
import br.com.mpr.ws.entity.ProdutoImagemDestaqueEntity;
import br.com.mpr.ws.properties.MprWsProperties;
import br.com.mpr.ws.vo.ProdutoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class ProdutoServiceImpl implements ProdutoService {


    @Resource(name = "ProdutoService.findAllProduto")
    private String findAllProduto;

    @Resource(name = "ProdutoService.getProdutoById")
    private String getProdutoById;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private MprWsProperties properties;

    @Override
    public List<ProdutoVo> listAll() {

        List<ProdutoVo> list = commonDao.findByNativeQuery(findAllProduto,ProdutoVo.class);

        return list;
    }

    @Override
    public ProdutoVo getProdutoById(Long id) {
        List<ProdutoVo> result = commonDao.findByNativeQuery(getProdutoById,
                ProdutoVo.class,
                new String[]{"id"},
                new Object[]{id}, true);

        ProdutoVo vo = null;
        if (result.size() > 0){
            vo = result.get(0);
            vo.setImgSemFoto(properties.getImgSemFoto());
            vo.setListUrlFotoDestaque(this.getListFotoDestaque(vo.getId()));
        }
        return vo;
    }

    private List<String> getListFotoDestaque(Long idProduto) {
        List<String> listFotoDestaque = new ArrayList<>();
        List<ProdutoImagemDestaqueEntity> listImgDestaque = commonDao.findByProperties(
                ProdutoImagemDestaqueEntity.class,
                new String[]{"produto.id"},
                new Object[]{idProduto});

        if (!CollectionUtils.isEmpty(listImgDestaque)){
            for (ProdutoImagemDestaqueEntity e : listImgDestaque){
                listFotoDestaque.add(properties.getBaseUrlDestaque() + e.getImg());
            }
        }
        return listFotoDestaque;
    }
}

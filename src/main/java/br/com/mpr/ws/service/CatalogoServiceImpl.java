package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.CatalogoEntity;
import br.com.mpr.ws.vo.ImagemExclusivaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class CatalogoServiceImpl implements CatalogoService {


    @Autowired
    private CommonDao commonDao;

    @Override
    public List<ImagemExclusivaVo> listImgExclusivasByCatalogo(Long idCatalogoGrupo) {
        List<CatalogoEntity> list = commonDao.findByProperties(CatalogoEntity.class,
                new String[]{"idCatalogoGrupo","ativo"},
                new Object[]{idCatalogoGrupo, Boolean.TRUE});

        return ImagemExclusivaVo.toVo(list);
    }
}

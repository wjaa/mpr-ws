package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.EmbalagemEntity;
import br.com.mpr.ws.entity.MprParameterType;
import br.com.mpr.ws.entity.ProdutoEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wagner on 13/01/19.
 */
@Service
public class EmbalagemServiceImpl implements EmbalagemService {

    private static final Log LOG = LogFactory.getLog(EmbalagemServiceImpl.class);

    @Autowired
    private MprParameterService mprParameterService;

    @Autowired
    private CommonDao commonDao;

    @Resource(name = "EmbalagemService.findEmbalagem")
    private String QUERY_FIND_EMBALAGEM;


    @Override
    public EmbalagemEntity getEmbalagem(List<ProdutoEntity> produtos) {

        Assert.notEmpty(produtos,"Não existe produtos na lista");
        Double maxComp = 0.0;
        Double maxLarg = 0.0;
        Double sumAlt = 0.0;

        Integer margemProtecao = mprParameterService.getParameterInteger(
                MprParameterType.MARGEM_PROTECAO,4);

        for (ProdutoEntity p : produtos){
            Double cp = p.getComp() + margemProtecao;
            Double lp = p.getLarg() + margemProtecao;
            if (cp > maxComp){
                maxComp = cp;
            }
            if (lp > maxLarg){
                maxLarg = p.getLarg();
            }
            sumAlt += p.getAlt() + margemProtecao;

        }

        EmbalagemEntity embalagemEntity = commonDao.findByNativeQuerySingleResult(
                QUERY_FIND_EMBALAGEM,
                EmbalagemEntity.class,
                new String[]{"comp","larg","alt"},
                new Object[]{maxComp,maxLarg,sumAlt});

        if (embalagemEntity == null){
            LOG.warn("m=getEmbalagem, nenhuma embalagem encontrada para c=" + maxComp +
                    ", l=" + maxLarg + ", a=" + sumAlt);
            LOG.warn("m=getEmbalagem, criando embalagem aproximada.");
            embalagemEntity = new EmbalagemEntity();
            embalagemEntity.setAlt(sumAlt);
            embalagemEntity.setComp(maxComp * produtos.size());
            embalagemEntity.setLarg(maxLarg * produtos.size());

        }

        return embalagemEntity;
    }
}

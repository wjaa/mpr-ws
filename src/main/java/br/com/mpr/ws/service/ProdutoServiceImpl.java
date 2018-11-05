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


    /**
     * Lista todos os produtos ativos no cadastro.
     * O Objeto ProdutoVo tem um propriedade quantidade (ProdutoVo.quantidade) que indica a quantidade em estoque no
     * momento da listagem dos produtos. Ele exclui produtos que estão no carrinho de outros clientes.
     *
     * Então essa listagem pode ter um falso positivo, caso dois clientes estão com o produto no carrinho e um terceiro,
     * liste os produtos, o terceiro cliente receberá um produto esgotado, caso ele liste novamente, os dois clientes
     * retiraram o produto do carrinho, esse produto estará disponível novamente.
     * TODO ESTUDAR MELHOR ESSA FORMA DE CONTROLAR O ESTOQUE.
     * @return
     */
    @Override
    public List<ProdutoVo> listAll() {

        List<ProdutoVo> list = commonDao.findByNativeQuery(findAllProduto,ProdutoVo.class);

        return list;
    }


    /**
     * Lista um produto pelo ID.
     * @param id
     * @return
     */
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

package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.EstoqueItemEntity;
import br.com.mpr.ws.entity.ProdutoEntity;
import br.com.mpr.ws.entity.ProdutoImagemDestaqueEntity;
import br.com.mpr.ws.entity.TipoProdutoEntity;
import br.com.mpr.ws.properties.MprWsProperties;
import br.com.mpr.ws.vo.ProdutoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class ProdutoServiceImpl implements ProdutoService {

    /**
     * AS QUERIES FICAM EM resources/queries/queries.xml
     */
    @Resource(name = "ProdutoService.findAllProduto")
    private String QUERY_ALL_PRODUTO;
    @Resource(name = "ProdutoService.getProdutoById")
    private String QUERY_PRODUTO_BY_ID;
    @Resource(name = "ProdutoService.getProdutosRelacionados")
    private String QUERY_PRODUTOS_RELACIONADOS;
    @Resource(name = "ProdutoService.getProdutoEmEstoque")
    private String QUERY_PRODUTO_EM_ESTOQUE;
    @Resource(name = "ProdutoService.findProduto")
    private String QUERY_FIND_PRODUTO;
    @Resource(name = "ProdutoService.findByLancamento")
    private String FIND_BY_LANCAMENTO;
    @Resource(name = "ProdutoService.findByPopular")
    private String FIND_BY_POPULAR;
    @Resource(name = "ProdutoService.orderByMenorPreco")
    private String ORDER_BY_MENOR_PRECO;
    @Resource(name = "ProdutoService.orderByMaiorPreco")
    private String ORDER_BY_MAIOR_PRECO;

    /***************************************************/



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
     * @return
     */
    @Override
    public List<ProdutoVo> listAll() {

        List<ProdutoVo> list = commonDao.findByNativeQuery(QUERY_ALL_PRODUTO,ProdutoVo.class);

        return list;
    }


    /**
     * Lista um produto pelo ID.
     * @param id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ProdutoVo getProdutoById(Long id) {
        List<ProdutoVo> result = commonDao.findByNativeQuery(QUERY_PRODUTO_BY_ID,
                ProdutoVo.class,
                new String[]{"id"},
                new Object[]{id}, true);

        ProdutoVo vo = null;
        if (result.size() > 0){
            vo = result.get(0);
            vo.setImgDestaque(properties.getBaseUrlDestaque() + vo.getImgDestaque());
            vo.setImgPreview(properties.getBaseUrlPreview() + vo.getImgPreview());
            vo.setImgSemFoto(properties.getImgSemFoto());
            vo.setListImgDestaque(this.getListFotoDestaque(vo.getId()));
            vo.setProdutosRelacionados(this.getProdutosRelacionados(vo.getId()));
        }
        return vo;
    }



    @Override
    public EstoqueItemEntity getProdutoEmEstoque(Long idProduto) {
        List<EstoqueItemEntity> result = commonDao.findByNativeQuery(QUERY_PRODUTO_EM_ESTOQUE,
                EstoqueItemEntity.class,
                new String[]{"idProduto"},
                new Object[]{idProduto});

        if (result.size() > 0){
            return result.get(0);
        }
        return null;
    }



    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<ProdutoVo> getProdutosRelacionados(Long idProduto) {
        List<ProdutoVo> result = commonDao.findByNativeQuery(QUERY_PRODUTOS_RELACIONADOS,
                ProdutoVo.class,
                new String[]{"id"},
                new Object[]{idProduto}, true);
        this.addImgs(result);
        return result;

    }



    @Override
    public boolean isAcessorio(Long idProduto) {
        ProdutoEntity produtoEntity = commonDao.get(ProdutoEntity.class,idProduto);
        if (produtoEntity != null){
            TipoProdutoEntity tipoProdutoEntity = commonDao.get(TipoProdutoEntity.class,
                    produtoEntity.getIdTipoProduto());
            return tipoProdutoEntity != null && tipoProdutoEntity.getAcessorio() != null ?
                    tipoProdutoEntity.getAcessorio() : true;
        }
        return true;
    }

    @Override
    public List<ProdutoVo> listLancamentos(int limite) {
        List<ProdutoVo> result = commonDao.findByNativeQuery(QUERY_FIND_PRODUTO + FIND_BY_LANCAMENTO,
                ProdutoVo.class,
                new String[]{"limite"},
                new Object[]{limite}, true);
        this.addImgs(result);
        return result;
    }

    @Override
    public List<ProdutoVo> listPopulares(int limite) {
        List<ProdutoVo> result = commonDao.findByNativeQuery(QUERY_FIND_PRODUTO + FIND_BY_POPULAR,
                ProdutoVo.class,
                new String[]{"limite"},
                new Object[]{limite}, true);
        this.addImgs(result);
        return result;
    }

    @Override
    public List<ProdutoVo> listProdutos(OrderBy orderBy, int limite) {
        List<ProdutoVo> result = commonDao.findByNativeQuery(QUERY_FIND_PRODUTO +
                        (OrderBy.MAIOR_PRECO.equals(orderBy) ? ORDER_BY_MAIOR_PRECO : ORDER_BY_MENOR_PRECO) ,
                ProdutoVo.class,
                new String[]{"limite"},
                new Object[]{limite}, true);
        this.addImgs(result);
        return result;
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

    private void addImgs(List<ProdutoVo> result) {
        for (ProdutoVo vo : result){
            this.addImgs(vo);
        }
    }

    private void addImgs(ProdutoVo vo) {
            vo.setImgDestaque(properties.getBaseUrlDestaque() + vo.getImgDestaque());
            vo.setImgPreview(properties.getBaseUrlPreview() + vo.getImgPreview());
            vo.setImgSemFoto(properties.getImgSemFoto());
            vo.setListImgDestaque(this.getListFotoDestaque(vo.getId()));
    }

}

package br.com.mpr.ws.service;


import br.com.mpr.ws.entity.EstoqueItemEntity;
import br.com.mpr.ws.vo.PageVo;
import br.com.mpr.ws.vo.ProdutoVo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */

public interface ProdutoService {


    ProdutoVo getProdutoDestaque();

    enum OrderBy {MAIOR_PRECO, MENOR_PRECO}

    List<ProdutoVo> listAll();

    PageVo listAllPaged(int pageSize, int page);

    ProdutoVo getProdutoById(Long id);

    EstoqueItemEntity getProdutoEmEstoque(Long idProduto);

    List<ProdutoVo> getProdutosRelacionados(Long idProduto);

    boolean isAcessorio(Long idProduto);

    List<ProdutoVo> listLancamentos(int limite);

    List<ProdutoVo> listPopulares(int limite);

    List<ProdutoVo> listProdutos(OrderBy orderBy, int limite);


}

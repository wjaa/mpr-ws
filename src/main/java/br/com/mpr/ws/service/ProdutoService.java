package br.com.mpr.ws.service;


import br.com.mpr.ws.entity.EstoqueItemEntity;
import br.com.mpr.ws.vo.ProdutoVo;

import java.util.List;

/**
 *
 */

public interface ProdutoService {


    enum OrderBy {MAIOR_PRECO, MENOR_PRECO}

    List<ProdutoVo> listAll();

    ProdutoVo getProdutoById(Long id);

    EstoqueItemEntity getProdutoEmEstoque(Long idProduto);

    List<ProdutoVo> getProdutosRelacionados(Long idProduto);

    boolean isAcessorio(Long idProduto);

    List<ProdutoVo> listLancamentos(int limite);

    List<ProdutoVo> listPopulares(int limite);

    List<ProdutoVo> listProdutos(OrderBy orderBy, int limite);


}

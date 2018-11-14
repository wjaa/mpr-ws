package br.com.mpr.ws.service;


import br.com.mpr.ws.entity.EstoqueItemEntity;
import br.com.mpr.ws.vo.ProdutoVo;

import java.util.List;

/**
 *
 */

public interface ProdutoService {


    List<ProdutoVo> listAll();

    ProdutoVo getProdutoById(Long id);

    EstoqueItemEntity getProdutoEmEstoque(Long idProduto);
}

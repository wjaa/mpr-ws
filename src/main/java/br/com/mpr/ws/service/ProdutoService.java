package br.com.mpr.ws.service;


import br.com.mpr.ws.entity.EstoqueItemEntity;
import br.com.mpr.ws.vo.PageVo;
import br.com.mpr.ws.vo.ProdutoVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 *
 */

public interface ProdutoService {


    enum OrderBy {MAIOR_PRECO, MENOR_PRECO;

        public static OrderBy byId(int id) {
            for (OrderBy o : values() ){
                if (o.ordinal() == id ){
                    return o;
                }
            }
            return MAIOR_PRECO;
        }
    }

    ProdutoVo getProdutoDestaque();

    List<ProdutoVo> listAll();

    PageVo listAllPaged(Pageable pageable);

    ProdutoVo getProdutoById(Long id);

    EstoqueItemEntity getProdutoEmEstoque(Long idProduto);

    List<ProdutoVo> getProdutosRelacionados(Long idProduto);

    boolean isAcessorio(Long idProduto);

    List<ProdutoVo> listLancamentos(int limite);

    List<ProdutoVo> listPopulares(int limite);

    List<ProdutoVo> listProdutos(OrderBy orderBy, int limite);

    PageVo findProdutoByNameOrDesc(String param, Pageable pageable, OrderBy orderBy);


}

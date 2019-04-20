package br.com.mpr.ws.rest;

import br.com.mpr.ws.service.ProdutoService;
import br.com.mpr.ws.vo.ProdutoVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/core")
public class ProdutoRest extends BaseRest{

    private static final Log LOG = LogFactory.getLog(ProdutoRest.class);


    @Autowired
    private ProdutoService produtoService;


    @RequestMapping(value = "/produto/all",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public List<ProdutoVo> getAllProduto(){
        return this.produtoService.listAll();
    }


    @RequestMapping(value = "/produto/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public ProdutoVo getProdutoById(@PathVariable Long id){
        return this.produtoService.getProdutoById(id);
    }


    @RequestMapping(value = "/produto/lancamento/{limite}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public List<ProdutoVo> findByLancamento(@PathVariable Integer limite){
        return this.produtoService.listLancamentos(limite == null ? 10 : limite);
    }

    @RequestMapping(value = "/produto/popular/{limite}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public List<ProdutoVo> findByPopular(@PathVariable Integer limite){
        return this.produtoService.listPopulares(limite == null ? 10 : limite);
    }

    @RequestMapping(value = "/produto/menorPreco/{limite}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public List<ProdutoVo> findByMenorPreco(@PathVariable Integer limite){
        return this.produtoService.listProdutos(ProdutoService.OrderBy.MENOR_PRECO,
                limite == null ? 10 : limite);
    }

    @RequestMapping(value = "/produto/maiorPreco/{limite}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public List<ProdutoVo> findByMaiorPreco(@PathVariable Integer limite){
        return this.produtoService.listProdutos(ProdutoService.OrderBy.MAIOR_PRECO,
                limite == null ? 10 : limite);
    }

}

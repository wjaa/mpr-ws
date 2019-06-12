package br.com.mpr.ws.rest;

import br.com.mpr.ws.service.ProdutoService;
import br.com.mpr.ws.vo.PageVo;
import br.com.mpr.ws.vo.ProdutoVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @RequestMapping(value = "/produto/all/{limite}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public List<ProdutoVo> getAllProduto(@PathVariable Integer limite){
        return this.produtoService.listAll(limite == null ? 10 : limite);
    }

    @RequestMapping(value = "/produto/all/{page}/{size}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public PageVo getAllProdutoPaged(@PathVariable int page, @PathVariable int size){
        return this.produtoService.listAllPaged(PageRequest.of(page-1,size));
    }

    @RequestMapping(value = "/produto/find/{param}/{page}/{size}/{orderBy}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public PageVo findProdutoPaged(@PathVariable String param, @PathVariable int page,
                                     @PathVariable int size,
                                     @PathVariable int orderBy){
        return this.produtoService.findProdutoByNameOrDesc(param,PageRequest.of(page-1,size),
                ProdutoService.OrderBy.byId(orderBy));
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

    @RequestMapping(value = "/produto/destaque",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public ProdutoVo getDestaque(){
        return this.produtoService.getProdutoDestaque();
    }


    @RequestMapping(value = "/produto/addImagem",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public ProdutoVo addImagem(){
        return null;//this.produtoService.getProdutoDestaque();
    }



}

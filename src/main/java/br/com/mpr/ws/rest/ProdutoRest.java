package br.com.mpr.ws.rest;

import br.com.mpr.ws.entity.*;
import br.com.mpr.ws.exception.AdminServiceException;
import br.com.mpr.ws.service.AdminService;
import br.com.mpr.ws.service.ProdutoService;
import br.com.mpr.ws.vo.ProdutoVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("/api/v1/core")
public class ProdutoRest extends BaseRest{

    private static final Log LOG = LogFactory.getLog(ProdutoRest.class);


    @Autowired
    private ProdutoService produtoService;


    //TODO CRIEI ESSE METHOD APENAS PARA TESTES...NAO VAMOS LISTAR TODOS OS PRODUTOS ASSIM NA API NÉ??

    @RequestMapping(value = "/produto/all",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public List<ProdutoVo> getAllProduto() throws AdminServiceException {
        return this.produtoService.listAll();
    }


}

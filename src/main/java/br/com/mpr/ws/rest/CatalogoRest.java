package br.com.mpr.ws.rest;

import br.com.mpr.ws.service.CatalogoService;
import br.com.mpr.ws.vo.ImagemExclusivaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/core")
public class CatalogoRest extends BaseRest {


    @Autowired
    private CatalogoService catalogoService;

    @RequestMapping(value = "/imagensExclusivas/byCatalogo/{idCatalogo}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public List<ImagemExclusivaVo> findImagensExclusivasByCatalogo(@PathVariable Long idCatalogo){
        return this.catalogoService.listImgExclusivasByCatalogo(idCatalogo);
    }


}

package br.com.mpr.ws.rest;

import br.com.mpr.ws.entity.EmbalagemEntity;
import br.com.mpr.ws.entity.FreteType;
import br.com.mpr.ws.entity.ProdutoEntity;
import br.com.mpr.ws.service.EmbalagemService;
import br.com.mpr.ws.service.FreteService;
import br.com.mpr.ws.service.ProdutoService;
import br.com.mpr.ws.vo.ResultFreteVo;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/core")
public class FreteRest extends BaseRest{


    @Autowired
    private FreteService freteService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private EmbalagemService embalagemService;


    @RequestMapping(value = "/frete/{type}/{idProduto}/{cep}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public ResultFreteVo calcFrete(@PathVariable String type,
            @PathVariable Long idProduto, @PathVariable String cep){
        ProdutoEntity produto = produtoService.getProdutoEntityById(idProduto);
        EmbalagemEntity embalagem = embalagemService.getEmbalagem(Arrays.asList(produto));
        return this.freteService.calcFrete(new FreteService.FreteParam(
                FreteType.valueOf(type.toUpperCase()),
                cep,
                produto.getPeso(),
                embalagem.getComp(),
                embalagem.getLarg(),
                embalagem.getAlt()
        ));
    }

    @RequestMapping(value = "/frete/all/{idProduto}/{cep}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public List<ResultFreteVo> calcFrete(@PathVariable Long idProduto, @PathVariable String cep){
        ProdutoEntity produto = produtoService.getProdutoEntityById(idProduto);
        EmbalagemEntity embalagem = embalagemService.getEmbalagem(Arrays.asList(produto));
        List<ResultFreteVo> fretesCalculados = new ArrayList<>(FreteType.values().length);
        for (FreteType tipoFrete : FreteType.values()){
            fretesCalculados.add(
                    this.freteService.calcFrete(new FreteService.FreteParam(
                    tipoFrete,
                    cep,
                    produto.getPeso(),
                    embalagem.getComp(),
                    embalagem.getLarg(),
                    embalagem.getAlt()
                ))
            );
        }

        return fretesCalculados;
    }

}

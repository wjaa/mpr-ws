package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.vo.ImagemExclusivaVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class CatalogoServiceImplTest extends BaseDBTest {

    @Autowired
    private CatalogoService catalogoService;


    @Test
    public void listImgExclusivasByCatalogo() {

        List<ImagemExclusivaVo> result = catalogoService.listImgExclusivasByCatalogo(1l);
        Assert.assertNotNull(result);
        Assert.assertEquals(3, result.size());
        for(ImagemExclusivaVo vo : result){
            Assert.assertNotNull(vo);
            Assert.assertNotNull(vo.getIdCatalogo());
            Assert.assertEquals(new Long(1l), vo.getIdCatalogoGrupo());
            Assert.assertNotNull(vo.getIdCatalogoGrupo());

            Assert.assertNotNull(vo.getDescricao());
            Assert.assertNotNull(vo.getNomeCatalogo());
            Assert.assertNotNull(vo.getUrlImg());
        }


        result = catalogoService.listImgExclusivasByCatalogo(2l);
        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.size());
        for(ImagemExclusivaVo vo : result){
            Assert.assertNotNull(vo);
            Assert.assertNotNull(vo.getIdCatalogo());
            Assert.assertEquals(new Long(2l), vo.getIdCatalogoGrupo());
            Assert.assertNotNull(vo.getIdCatalogoGrupo());

            Assert.assertNotNull(vo.getDescricao());
            Assert.assertNotNull(vo.getNomeCatalogo());
            Assert.assertNotNull(vo.getUrlImg());
        }


    }
}
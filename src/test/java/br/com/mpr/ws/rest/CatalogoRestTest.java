package br.com.mpr.ws.rest;

import br.com.mpr.ws.BaseMvcTest;
import br.com.mpr.ws.utils.ObjectUtils;
import br.com.mpr.ws.vo.CatalogoVo;
import br.com.mpr.ws.vo.ImagemExclusivaVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;


public class CatalogoRestTest extends BaseMvcTest {

    @Test
    public void findImagensExclusivasByCatalogo() {
        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/core/imagensExclusivas/byCatalogo/" + 1);
            ImagemExclusivaVo[] listResult = ObjectUtils.fromJSON(ra.andReturn().getResponse().getContentAsString(),
                    ImagemExclusivaVo[].class);
            Assert.assertNotNull(listResult);
            Assert.assertEquals(3, listResult.length);

            for(ImagemExclusivaVo vo : listResult){
                Assert.assertNotNull(vo);
                Assert.assertNotNull(vo.getIdImagem());
                Assert.assertEquals(new Long(1l), vo.getIdCatalogoGrupo());
                Assert.assertNotNull(vo.getIdCatalogoGrupo());

                Assert.assertNotNull(vo.getDescricao());
                Assert.assertNotNull(vo.getNomeCatalogo());
                Assert.assertNotNull(vo.getUrlImg());
            }


            ra = getMvcGetResultActions("/api/v1/core/imagensExclusivas/byCatalogo/" + 2);
            listResult = ObjectUtils.fromJSON(ra.andReturn().getResponse().getContentAsString(),
                    ImagemExclusivaVo[].class);
            Assert.assertNotNull(listResult);
            Assert.assertEquals(2, listResult.length);
            for(ImagemExclusivaVo vo : listResult){
                Assert.assertNotNull(vo);
                Assert.assertNotNull(vo.getIdImagem());
                Assert.assertEquals(new Long(2l), vo.getIdCatalogoGrupo());
                Assert.assertNotNull(vo.getIdCatalogoGrupo());

                Assert.assertNotNull(vo.getDescricao());
                Assert.assertNotNull(vo.getNomeCatalogo());
                Assert.assertNotNull(vo.getUrlImg());
            }
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }


    @Test
    public void listAllCatalogo() {
        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/core/catalogo/all");
            CatalogoVo[] listResult = ObjectUtils.fromJSON(ra.andReturn().getResponse().getContentAsString(),
                    CatalogoVo[].class);
            Assert.assertNotNull(listResult);
            Assert.assertEquals(2, listResult.length);

            for(CatalogoVo vo : listResult){
                Assert.assertNotNull(vo);
                Assert.assertNotNull(vo.getId());
                Assert.assertNotNull(vo.getNome());
            }

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }
}
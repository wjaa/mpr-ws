package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.CatalogoEntity;
import br.com.mpr.ws.entity.ProdutoPreviewEntity;
import br.com.mpr.ws.entity.SessionEntity;
import br.com.mpr.ws.exception.ImagemServiceException;
import br.com.mpr.ws.helper.ProdutoHelper;
import br.com.mpr.ws.vo.AnexoVo;
import br.com.mpr.ws.vo.PreviewForm;
import br.com.mpr.ws.vo.ProdutoVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;


public class ProdutoPreviewServiceImplTest extends BaseDBTest {

    @MockBean
    private ImagemService imagemService;

    @Autowired
    private ProdutoPreviewService previewService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private CommonDao commonDao;


    @Before
    public void mockImageService(){
        Mockito.when(
                imagemService.getUrlPreviewCliente(Mockito.any(String.class))
        ).thenReturn("http://stc.meuportaretrato.com/img/preview_cliente/1213432142134.png");

        try {
            Mockito.when(imagemService.createPreviewCliente(Mockito.any(String.class),
                    Mockito.any(List.class),Mockito.any(List.class)))
                    .thenReturn("previewCliente.jpg");

            Mockito.when(
                    imagemService.uploadFotoCliente(Mockito.any(),Mockito.anyString())
            ).thenReturn("fotoCliente.png");

            Mockito.when(
                    imagemService.getUrlFotoCliente(Mockito.anyString())
            ).thenReturn("http://stc.meuportaretrato.com/img/cliente/teste.png");

            Mockito.when(
                    imagemService.getUrlFotoCatalogo(Mockito.anyString())
            ).thenReturn("http://stc.meuportaretrato.com/img/cliente/teste.png");


        } catch (ImagemServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddFotoClienteLogado(){
        PreviewForm previewForm = new PreviewForm();
        previewForm.setIdCliente(1l);
        previewForm.setIdProduto(1l);
        previewForm.setAnexos(this.createFotoAnexo());
        try{
            ProdutoVo produtoVo = previewService.addFoto(previewForm);
            Assert.assertNotNull(produtoVo.getImgPreviewCliente());
            ProdutoHelper.validarInfoProduto(produtoVo);
            ProdutoPreviewEntity produtoPreview = previewService.getProdutoPreviewByIdCliente(previewForm.getIdCliente());

            Assert.assertNotNull(produtoPreview);
            Assert.assertNotNull(produtoPreview.getFotoPreview());
            Assert.assertNotNull(produtoPreview.getAnexos());
            Assert.assertEquals(1, produtoPreview.getAnexos().size());
            Assert.assertEquals(new Long(1l), produtoPreview.getIdCliente());
            Assert.assertNull(produtoPreview.getIdSession());
        }catch (Exception ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }


    @Test
    public void testAddFotoCatalogoClienteLogado(){
        PreviewForm previewForm = new PreviewForm();
        previewForm.setIdCliente(1l);
        previewForm.setIdProduto(1l);
        previewForm.setAnexos(this.createCatalogoAnexo());
        try{
            ProdutoVo produtoVo = previewService.addFoto(previewForm);
            Assert.assertNotNull(produtoVo.getImgPreviewCliente());
            ProdutoHelper.validarInfoProduto(produtoVo);
            ProdutoPreviewEntity produtoPreview = previewService.getProdutoPreviewByIdCliente(previewForm.getIdCliente());
            Assert.assertNotNull(produtoPreview);
            Assert.assertNotNull(produtoPreview.getFotoPreview());
            Assert.assertNotNull(produtoPreview.getAnexos());
            Assert.assertEquals(1, produtoPreview.getAnexos().size());
            Assert.assertEquals(new Long(1l), produtoPreview.getIdCliente());
            Assert.assertNull(produtoPreview.getIdSession());
        }catch (Exception ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }

    @Test
    public void testAddFotoClienteSession(){
        PreviewForm previewForm = new PreviewForm();
        SessionEntity session = sessionService.createSession();
        previewForm.setSessionToken(session.getSessionToken());
        previewForm.setIdProduto(1l);
        previewForm.setAnexos(this.createFotoAnexo());
        try{
            ProdutoVo produtoVo = previewService.addFoto(previewForm);
            Assert.assertNotNull(produtoVo.getImgPreviewCliente());
            ProdutoHelper.validarInfoProduto(produtoVo);
            ProdutoPreviewEntity produtoPreview = previewService.getProdutoPreviewBySessionToken(previewForm.getSessionToken());
            Assert.assertNotNull(produtoPreview);
            Assert.assertNotNull(produtoPreview.getFotoPreview());
            Assert.assertNotNull(produtoPreview.getAnexos());
            Assert.assertEquals(1, produtoPreview.getAnexos().size());
            Assert.assertNull(produtoPreview.getIdCliente());
            Assert.assertEquals(session.getId(), produtoPreview.getIdSession());
        }catch (Exception ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }

    @Test
    public void testAddFotoCatalogoClienteSession(){
        PreviewForm previewForm = new PreviewForm();
        SessionEntity session = sessionService.createSession();
        previewForm.setSessionToken(session.getSessionToken());
        previewForm.setIdProduto(1l);
        previewForm.setAnexos(this.createCatalogoAnexo());
        try{
            ProdutoVo produtoVo = previewService.addFoto(previewForm);
            Assert.assertNotNull(produtoVo.getImgPreviewCliente());
            ProdutoHelper.validarInfoProduto(produtoVo);
            ProdutoPreviewEntity produtoPreview = previewService.getProdutoPreviewBySessionToken(previewForm.getSessionToken());
            Assert.assertNotNull(produtoPreview);
            Assert.assertNotNull(produtoPreview.getFotoPreview());
            Assert.assertNotNull(produtoPreview.getAnexos());
            Assert.assertEquals(1, produtoPreview.getAnexos().size());
            Assert.assertNull(produtoPreview.getIdCliente());
            Assert.assertEquals(session.getId(), produtoPreview.getIdSession());
        }catch (Exception ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }

    @Test
    public void testAddFotoECatalogoClienteLogado(){
        PreviewForm previewForm = new PreviewForm();
        SessionEntity session = sessionService.createSession();
        previewForm.setSessionToken(session.getSessionToken());
        previewForm.setIdProduto(1l);
        previewForm.setAnexos(this.createFotoECatalogoAnexo());
        try{
            ProdutoVo produtoVo = previewService.addFoto(previewForm);
            Assert.assertNotNull(produtoVo.getImgPreviewCliente());
            ProdutoHelper.validarInfoProduto(produtoVo);
            ProdutoPreviewEntity produtoPreview = previewService.getProdutoPreviewBySessionToken(previewForm.getSessionToken());
            Assert.assertNotNull(produtoPreview);
            Assert.assertNotNull(produtoPreview.getFotoPreview());
            Assert.assertNotNull(produtoPreview.getAnexos());
            Assert.assertEquals(2, produtoPreview.getAnexos().size());
            Assert.assertNull(produtoPreview.getIdCliente());
            Assert.assertEquals(session.getId(), produtoPreview.getIdSession());
        }catch (Exception ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }

    @Test
    public void testAddFotoClienteSessionExpirada(){
        PreviewForm previewForm = new PreviewForm();

        previewForm.setSessionToken("WWWWQQQQQ");
        previewForm.setIdProduto(1l);
        previewForm.setAnexos(this.createFotoAnexo());
        try{
            previewService.addFoto(previewForm);
            Assert.assertTrue("Não deveria ter chegado aqui", false);
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(), ex.getMessage().contains("Sessão não encontrada"));
        }
    }

    private List<AnexoVo> createFotoAnexo() {
        return Arrays.asList(new AnexoVo(new byte[]{123,13,123},"foto.jpg"));
    }
    private List<AnexoVo> createCatalogoAnexo() {
        return Arrays.asList(new AnexoVo(1l));
    }
    private List<AnexoVo> createFotoECatalogoAnexo() {
        return Arrays.asList(new AnexoVo(new byte[]{123,13,123},"foto.jpg"),new AnexoVo(1l));
    }
}

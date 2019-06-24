package br.com.mpr.ws.rest;

import br.com.mpr.ws.BaseMvcTest;
import br.com.mpr.ws.entity.SessionEntity;
import br.com.mpr.ws.exception.ImagemServiceException;
import br.com.mpr.ws.helper.ProdutoHelper;
import br.com.mpr.ws.service.ImagemService;
import br.com.mpr.ws.service.ProdutoService;
import br.com.mpr.ws.service.SessionService;
import br.com.mpr.ws.utils.ObjectUtils;
import br.com.mpr.ws.vo.AnexoVo;
import br.com.mpr.ws.vo.CarrinhoVo;
import br.com.mpr.ws.vo.PreviewForm;
import br.com.mpr.ws.vo.ProdutoVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class ProdutoPreviewRestTest extends BaseMvcTest {


    @Autowired
    private SessionService sessionService;

    @Autowired
    private ImagemService imagemService;

    @Autowired
    private ProdutoService produtoService;

    @Before
    public void before() throws ImagemServiceException {
        Mockito.when(
                imagemService.getUrlPreviewCliente("fotoPreviewCliente.png")
        ).thenReturn("http://stc.meuportaretrato.com/img/preview_cliente/1213432142134.png");
    }


    @Test
    public void addImagemClienteLogado() {

        try{

            String accessToken = obtainAccessTokenPassword();
            PreviewForm previewForm = new PreviewForm();
            previewForm.setIdProduto(1l);
            previewForm.setAnexos(createFotoAnexo());

            AnexoVo anexoVo = previewForm.getAnexos().get(0);
            String fotoPreview = produtoService.getImagemPreviewProdutoById(previewForm.getIdProduto());
            Mockito.when(
                    imagemService.createPreviewCliente(fotoPreview,Arrays.asList("fotoCliente.png"),
                            new ArrayList<>())
            ).thenReturn("fotoPreviewCliente.png");

            Mockito.when(
                    imagemService.uploadFotoCliente(anexoVo.getFoto(),anexoVo.getNomeArquivo())
            ).thenReturn("fotoCliente.png");


            String content = ObjectUtils.toJson(previewForm);
            ResultActions ra = getMvcPostResultAction("/api/v1/core/preview/addImagem", accessToken, content);

            String resultJson = ra.andReturn().getResponse().getContentAsString();

            ProdutoVo produtoVo = ObjectUtils.toObject(resultJson,ProdutoVo.class);

            ProdutoHelper.validarInfoProduto(produtoVo);
            Assert.assertNotNull(produtoVo.getImgPreviewCliente());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }

    @Test
    public void addImagemClienteNaoLogado() {
        try{
            SessionEntity sessionEntity =  sessionService.createSession();
            PreviewForm previewForm = new PreviewForm();
            previewForm.setIdProduto(1l);
            previewForm.setAnexos(createFotoAnexo());

            AnexoVo anexoVo = previewForm.getAnexos().get(0);
            String fotoPreview = produtoService.getImagemPreviewProdutoById(previewForm.getIdProduto());
            Mockito.when(
                    imagemService.createPreviewCliente(fotoPreview,Arrays.asList("fotoCliente.png"),
                            new ArrayList<>())
            ).thenReturn("fotoPreviewCliente.png");

            Mockito.when(
                    imagemService.uploadFotoCliente(anexoVo.getFoto(),anexoVo.getNomeArquivo())
            ).thenReturn("fotoCliente.png");

            String content = ObjectUtils.toJson(previewForm);
            ResultActions ra = getMvcPostResultAction("/api/v1/core/preview/addImagem/" +
                    sessionEntity.getSessionToken(), content);

            String resultJson = ra.andReturn().getResponse().getContentAsString();

            ProdutoVo produtoVo = ObjectUtils.toObject(resultJson,ProdutoVo.class);

            ProdutoHelper.validarInfoProduto(produtoVo);
            Assert.assertNotNull(produtoVo.getImgPreviewCliente());
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }

    @Override
    protected AppUser getAppUser() {
        return getAppUserClient();
    }

    private List<AnexoVo> createFotoAnexo() {
        return Arrays.asList(new AnexoVo(new byte[]{123,13,123},"foto.jpg"));
    }
}
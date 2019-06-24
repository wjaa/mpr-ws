package br.com.mpr.ws.rest;

import br.com.mpr.ws.BaseMvcTest;
import br.com.mpr.ws.entity.SessionEntity;
import br.com.mpr.ws.exception.ImagemServiceException;
import br.com.mpr.ws.helper.ProdutoHelper;
import br.com.mpr.ws.service.ImagemService;
import br.com.mpr.ws.service.ProdutoService;
import br.com.mpr.ws.service.SessionService;
import br.com.mpr.ws.utils.ObjectUtils;
import br.com.mpr.ws.utils.StringUtils;
import br.com.mpr.ws.vo.AnexoVo;
import br.com.mpr.ws.vo.CarrinhoVo;
import br.com.mpr.ws.vo.PreviewForm;
import br.com.mpr.ws.vo.ProdutoVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class ProdutoPreviewRestTest extends BaseMvcTest {


    @Autowired
    private SessionService sessionService;

    @MockBean
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
            ResultActions ra = getMvcPutResultAction("/api/v1/core/preview/addImagem", accessToken, content);

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
            ResultActions ra = getMvcPutResultAction("/api/v1/core/preview/addImagem/" +
                    sessionEntity.getSessionToken(), content);

            String resultJson = ra.andReturn().getResponse().getContentAsString();

            ProdutoVo produtoVo = ObjectUtils.toObject(resultJson,ProdutoVo.class);

            ProdutoHelper.validarInfoProduto(produtoVo);
            Assert.assertNotNull(produtoVo.getImgPreviewCliente());
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }


    /**
     *
     * TESTANDO A VALIDACAO DO FORM.
     * 1. Adicionar uma imagem sem produto associado
     * 2. Adicionar uma imagem sem foto(byte) e sem foto de catalogo(id_catalogo).
     * 3. Adicionar uma imagem com foto(byte) e sem nome do arquivo
     *
     */
    @Test
    public void addImagemValidation(){




        PreviewForm item = new PreviewForm();
        item.setIdCliente(1l);
        item.setAnexos(new ArrayList<>());
        AnexoVo anexoVo = new AnexoVo();
        anexoVo.setFoto(new byte[]{0,0,0,0,0});
        anexoVo.setNomeArquivo(StringUtils.createRandomHash() + ".png");
        item.getAnexos().add(anexoVo);

        try{

            Mockito.when(
                    imagemService.createPreviewCliente(Mockito.anyString(),Mockito.anyList(),
                            Mockito.anyList())
            ).thenReturn("fotoPreviewCliente.png");

            Mockito.when(
                    imagemService.uploadFotoCliente(Mockito.any(),Mockito.anyString())
            ).thenReturn("fotoCliente.png");


            String content = ObjectUtils.toJson(item);
            String accessToken = obtainAccessTokenPassword();
            //#1
            ResultActions ra = getMvcPutErrorResultAction("/api/v1/core/preview/addImagem",
                    accessToken, content);
            Assert.assertTrue(ra.andReturn().getResponse().getContentAsString(),
                    ra.andReturn().getResponse().getContentAsString().contains("Produto é obrigatório"));

            //#2
            item.setIdProduto(5l);
            item.getAnexos().get(0).setFoto(null);
            content = ObjectUtils.toJson(item);
            ra = getMvcPutErrorResultAction("/api/v1/core/preview/addImagem",accessToken, content);
            Assert.assertTrue(ra.andReturn().getResponse().getContentAsString(),
                    ra.andReturn().getResponse().getContentAsString().contains("Algum anexo está sem foto"));

            //#3
            item.getAnexos().get(0).setFoto(new byte[]{10,10,10});
            item.getAnexos().get(0).setNomeArquivo("");
            content = ObjectUtils.toJson(item);
            ra = getMvcPutErrorResultAction("/api/v1/core/preview/addImagem", accessToken, content);
            Assert.assertTrue(ra.andReturn().getResponse().getContentAsString(),
                    ra.andReturn().getResponse().getContentAsString().contains("Nome da imagem está vazio"));

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
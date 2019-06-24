package br.com.mpr.ws.rest;

import br.com.mpr.ws.BaseMvcTest;
import br.com.mpr.ws.entity.SessionEntity;
import br.com.mpr.ws.exception.ImagemServiceException;
import br.com.mpr.ws.service.ImagemService;
import br.com.mpr.ws.service.ProdutoPreviewService;
import br.com.mpr.ws.service.SessionService;
import br.com.mpr.ws.utils.ObjectUtils;
import br.com.mpr.ws.utils.StringUtils;
import br.com.mpr.ws.vo.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 *
 */
public class CarrinhoRestTest extends BaseMvcTest {


    @Autowired
    private SessionService sessionService;

    @MockBean
    private ImagemService imagemService;

    @Autowired
    private ProdutoPreviewService produtoPreviewService;


    @Before
    public void mockImageService(){
        //imagemService = Mockito.spy(imagemService);
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


    /**
     * USANDO UM CLIENTE NAO CADASTRADO
     * 1. Adicionar um produto no carrinho
     * 2. Resgatar o carrinho pelo keydevice e verificar se o produto está lá.
     *
     */
    @Test
    public void addItemCarrinhoClienteNaoLogado(){


        try{
            PreviewForm form = createItemCarrinhoFormClienteDinamico(5l);
            ProdutoVo produtoVo = produtoPreviewService.addFoto(form);
            Assert.assertNotNull(produtoVo.getImgPreviewCliente());

            String content = ObjectUtils.toJson(form);
            ResultActions ra = getMvcPutResultAction("/api/v1/core/carrinho/add/" + form.getSessionToken(), "");

            String resultJson = ra.andReturn().getResponse().getContentAsString();

            ResultActions raGet = getMvcGetResultActions("/api/v1/core/carrinho/" + form.getSessionToken());

            raGet.andExpect(content().json(resultJson));

            CarrinhoVo resultCarrinho = ObjectUtils.toObject(resultJson,CarrinhoVo.class);
            Assert.assertEquals(form.getSessionToken(),resultCarrinho.getSessionToken());
            Assert.assertNotNull(resultCarrinho.getIdCarrinho());
            Assert.assertEquals(1, resultCarrinho.getItems().size());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }


    }

    /**
     * USANDO UM CLIENTE CADASTRADO
     * 1. Adicionar um produto no carrinho
     * 2. Resgatar o carrinho pelo id do cliente e verificar se o produto está lá.
     *
     */
    @Test
    public void addItemCarrinhoClienteLogado(){

        PreviewForm item = createItemCarrinhoFormClienteLogado();

        try{

            ProdutoVo produtoVo = produtoPreviewService.addFoto(item);
            Assert.assertNotNull(produtoVo.getImgPreviewCliente());

            String accessToken = obtainAccessTokenPassword();
            //String content = ObjectUtils.toJson(item);
            ResultActions ra = getMvcPutResultAction("/api/v1/core/carrinho/add", accessToken, "");

            String resultJson = ra.andReturn().getResponse().getContentAsString();

            ResultActions raGet = getMvcGetResultActions("/api/v1/core/carrinho", accessToken);

            raGet.andExpect(content().json(resultJson));

            CarrinhoVo resultCarrinho = ObjectUtils.toObject(resultJson,CarrinhoVo.class);
            Assert.assertEquals(new Long(3),resultCarrinho.getIdCliente());
            Assert.assertNotNull(resultCarrinho.getIdCarrinho());
            Assert.assertEquals(1, resultCarrinho.getItems().size());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }


    /**
     *
     * TESTANDO A VALIDACAO DO FORM.
     * 1. Adicionar um produto no carrinho sem produto
     * 2. Adicionar um produto no carrinho sem foto e sem foto de catalogo.
     * 3. Adicionar um produto no carrinho com foto e sem nome
     * 4. Adicionar um produto no carrinho sem keydevice e sem idCliente
     *
     */
    @Test
    public void addItemCarrinhoValidation(){


        PreviewForm item = new PreviewForm();
        item.setIdCliente(1l);
        item.setAnexos(new ArrayList<>());
        AnexoVo anexoVo = new AnexoVo();
        anexoVo.setFoto(new byte[]{0,0,0,0,0});
        anexoVo.setNomeArquivo(StringUtils.createRandomHash() + ".png");
        item.getAnexos().add(anexoVo);

        try{

            String accessToken = obtainAccessTokenPassword();

            //#1
            String content = ObjectUtils.toJson(item);
            ResultActions ra = getMvcPutErrorResultAction("/api/v1/core/carrinho/add", accessToken, content);
            Assert.assertTrue(ra.andReturn().getResponse().getContentAsString().contains("Produto é obrigatório"));

            //#2
            item.setIdProduto(5l);
            item.getAnexos().get(0).setFoto(null);
            content = ObjectUtils.toJson(item);
            ra = getMvcPutErrorResultAction("/api/v1/core/carrinho/add",accessToken, content);
            Assert.assertTrue(ra.andReturn().getResponse().getContentAsString().contains("Para adicionar esse produto no carrinho, uma imagem é obrigatória"));

            //#3
            item.getAnexos().get(0).setFoto(new byte[]{10,10,10});
            item.getAnexos().get(0).setNomeArquivo("");
            content = ObjectUtils.toJson(item);
            ra = getMvcPutErrorResultAction("/api/v1/core/carrinho/add", accessToken, content);
            Assert.assertTrue(ra.andReturn().getResponse().getContentAsString().contains("Nome da imagem está vazio"));

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }


    }




    /**
     *
     * 1. Buscar o carrinho cliente NAO cadastrado e SEM produto.
     *
     */
    @Test
    public void getCarrinhoClienteNaoLogadoSemProduto(){
        SessionEntity sessionEntity = sessionService.createSession();
        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/core/carrinho/" + sessionEntity.getSessionToken());
            CarrinhoVo carrinhoVo = ObjectUtils.toObject(ra.andReturn().getResponse().getContentAsString(),CarrinhoVo.class);
            Assert.assertNotNull(carrinhoVo);
            Assert.assertNull(carrinhoVo.getItems());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }


    }

    /**
     * 1. Adicionar produto no carrinho do cliente nao cadastrado
     * 2. Buscar o carrinho para cliente NAO cadastrado.
     *
     */
    @Test
    public void getCarrinhoClienteNaoLogadoComProduto(){
        try{

            PreviewForm item1 = createItemCarrinhoFormClienteDinamico(5l);
            ProdutoVo produtoVo = produtoPreviewService.addFoto(item1);
            Assert.assertNotNull(produtoVo.getImgPreviewCliente());
            String content = ObjectUtils.toJson(item1);
            getMvcPutResultAction("/api/v1/core/carrinho/add/" + item1.getSessionToken(), "");

            ResultActions ra = getMvcGetResultActions("/api/v1/core/carrinho/" + item1.getSessionToken());
            CarrinhoVo carrinhoVo = ObjectUtils.toObject(ra.andReturn().getResponse().getContentAsString(),CarrinhoVo.class);
            Assert.assertNotNull(carrinhoVo);
            Assert.assertEquals(1,carrinhoVo.getItems().size());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }


    }


    /**
     *
     * 1. Buscar o carrinho do cliente logado e SEM produto.
     *
     */
    @Test
    public void getCarrinhoClienteLogadoSemProduto(){
        try{
            String accessToken = obtainAccessTokenPassword();
            ResultActions ra = getMvcGetResultActions("/api/v1/core/carrinho", accessToken);
            CarrinhoVo carrinhoVo = ObjectUtils.toObject(ra.andReturn().getResponse().getContentAsString(),CarrinhoVo.class);
            Assert.assertNotNull(carrinhoVo);
            Assert.assertNull(carrinhoVo.getItems());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }


    }

    /**
     * 1. Adicionar produto no carrinho do cliente logado
     * 2. Buscar o carrinho e verificar se existeum produto.
     *
     */
    @Test
    public void getCarrinhoClienteLogadoComProduto(){
        try{
            String accessToken = obtainAccessTokenPassword("testecarrinho@gmail.com","1234567");
            PreviewForm item1 = createItemCarrinhoFormClienteDinamico(5l);
            ProdutoVo produtoVo = produtoPreviewService.addFoto(item1);
            Assert.assertNotNull(produtoVo.getImgPreviewCliente());
            String content = ObjectUtils.toJson(item1);
            getMvcPutResultAction("/api/v1/core/carrinho/add",accessToken,"");

            ResultActions ra = getMvcGetResultActions("/api/v1/core/carrinho", accessToken);
            CarrinhoVo carrinhoVo = ObjectUtils.toObject(ra.andReturn().getResponse().getContentAsString(),CarrinhoVo.class);
            Assert.assertNotNull(carrinhoVo);
            Assert.assertEquals(1,carrinhoVo.getItems().size());


            ra = getMvcDeleteResultActions("/api/v1/core/carrinho/removeItem/" +
                    carrinhoVo.getItems().get(0).getId(), accessToken);
            carrinhoVo = ObjectUtils.toObject(ra.andReturn().getResponse().getContentAsString(),CarrinhoVo.class);
            Assert.assertNotNull(carrinhoVo);
            Assert.assertTrue(carrinhoVo.getItems().size() == 0);


        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }


    }




    /**
     * PARA UM CLIENTE NAO LOGADO.
     * 1. Adicionar produto no carrinho.
     * 2. Verificar se produto esta no carrinho
     * 3. Remover produto do carrinho.
     * 4. Buscar carrinho e verificar se está vazio.
     */
    @Test
    public void removeCarrinhoClienteNaoLogado(){
        try{
            //#1
            PreviewForm item1 = createItemCarrinhoFormClienteDinamico(5l);
            ProdutoVo produtoVo = produtoPreviewService.addFoto(item1);
            Assert.assertNotNull(produtoVo.getImgPreviewCliente());
            String content = ObjectUtils.toJson(item1);
            getMvcPutResultAction("/api/v1/core/carrinho/add/" + item1.getSessionToken(), "");

            //#2
            ResultActions ra = getMvcGetResultActions("/api/v1/core/carrinho/" + item1.getSessionToken());
            CarrinhoVo carrinhoVo = ObjectUtils.toObject(ra.andReturn().getResponse().getContentAsString(),CarrinhoVo.class);
            Assert.assertNotNull(carrinhoVo);
            Assert.assertEquals(item1.getSessionToken(),carrinhoVo.getSessionToken());
            Assert.assertTrue(carrinhoVo.getItems().size() == 1);

            //#3
            ra = getMvcDeleteResultActions("/api/v1/core/carrinho/removeItem/" +
                    carrinhoVo.getItems().get(0).getId() + "/" + item1.getSessionToken());
            carrinhoVo = ObjectUtils.toObject(ra.andReturn().getResponse().getContentAsString(),CarrinhoVo.class);
            Assert.assertNotNull(carrinhoVo);
            Assert.assertEquals(item1.getSessionToken(),carrinhoVo.getSessionToken());
            Assert.assertTrue(carrinhoVo.getItems().size() == 0);

            //#4
            ra = getMvcGetResultActions("/api/v1/core/carrinho/" + item1.getSessionToken());
            carrinhoVo = ObjectUtils.toObject(ra.andReturn().getResponse().getContentAsString(),CarrinhoVo.class);
            Assert.assertNotNull(carrinhoVo);
            Assert.assertEquals(item1.getSessionToken(),carrinhoVo.getSessionToken());
            Assert.assertTrue(carrinhoVo.getItems().size() == 0);



        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }


    /**
     * PARA UM CLIENTE LOGADO
     * 1. Adicionar produto no carrinho.
     * 2. Verificar se produto esta no carrinho
     * 3. Remover produto do carrinho.
     * 4. Buscar carrinho e verificar se está vazio.
     */
    @Test
    public void removeCarrinhoClienteLogado(){
        try{
            String accessToken = obtainAccessTokenPassword("testecarrinho@gmail.com","1234567");
            //#1
            PreviewForm item1 = createItemCarrinhoFormClienteLogado();
            ProdutoVo produtoVo = produtoPreviewService.addFoto(item1);
            Assert.assertNotNull(produtoVo.getImgPreviewCliente());
            getMvcPutResultAction("/api/v1/core/carrinho/add/", accessToken , "");

            //#2
            ResultActions ra = getMvcGetResultActions("/api/v1/core/carrinho", accessToken);
            CarrinhoVo carrinhoVo = ObjectUtils.toObject(ra.andReturn().getResponse().getContentAsString(),CarrinhoVo.class);
            Assert.assertNotNull(carrinhoVo);
            Assert.assertEquals(item1.getSessionToken(),carrinhoVo.getSessionToken());
            Assert.assertEquals(1, carrinhoVo.getItems().size());

            //#3
            ra = getMvcDeleteResultActions("/api/v1/core/carrinho/removeItem/" +
                    carrinhoVo.getItems().get(0).getId(), accessToken);
            carrinhoVo = ObjectUtils.toObject(ra.andReturn().getResponse().getContentAsString(),CarrinhoVo.class);
            Assert.assertNotNull(carrinhoVo);
            Assert.assertEquals(item1.getSessionToken(),carrinhoVo.getSessionToken());
            Assert.assertEquals(0, carrinhoVo.getItems().size());

            //#4
            ra = getMvcGetResultActions("/api/v1/core/carrinho", accessToken);
            carrinhoVo = ObjectUtils.toObject(ra.andReturn().getResponse().getContentAsString(),CarrinhoVo.class);
            Assert.assertNotNull(carrinhoVo);
            Assert.assertEquals(item1.getSessionToken(),carrinhoVo.getSessionToken());
            Assert.assertEquals(0, carrinhoVo.getItems().size());



        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }

    private PreviewForm createItemCarrinhoFormClienteLogado() {
        PreviewForm previewForm = new PreviewForm();
        previewForm.setIdProduto(5l);
        previewForm.setIdCliente(3l);
        previewForm.setAnexos(new ArrayList<>());
        AnexoVo anexoVo = new AnexoVo();
        anexoVo.setFoto(new byte[]{0,0,0,0,0});
        anexoVo.setNomeArquivo(StringUtils.createRandomHash() + ".png");
        previewForm.getAnexos().add(anexoVo);
        return previewForm;
    }

    private PreviewForm createItemCarrinhoFormClienteDinamico(Long idProduto) {
        SessionEntity sessionEntity = sessionService.createSession();

        PreviewForm previewForm = new PreviewForm();
        previewForm.setIdProduto(idProduto);
        previewForm.setSessionToken(sessionEntity.getSessionToken());
        previewForm.setAnexos(new ArrayList<>());
        AnexoVo anexoVo = new AnexoVo();
        anexoVo.setFoto(new byte[]{0,0,0,0,0});
        anexoVo.setNomeArquivo(StringUtils.createRandomHash() + ".png");
        previewForm.getAnexos().add(anexoVo);
        return previewForm;
    }


    protected AppUser getAppUser(){
        return getAppUserClient();
    }


}

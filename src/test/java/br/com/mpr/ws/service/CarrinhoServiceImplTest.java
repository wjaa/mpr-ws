package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.EstoqueItemEntity;
import br.com.mpr.ws.entity.SessionEntity;
import br.com.mpr.ws.exception.CarrinhoServiceException;
import br.com.mpr.ws.exception.ImagemServiceException;
import br.com.mpr.ws.exception.ProdutoPreviewServiceException;
import br.com.mpr.ws.service.thread.ClienteCarrinhoThreadMonitor;
import br.com.mpr.ws.utils.StringUtils;
import br.com.mpr.ws.vo.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CarrinhoServiceImplTest extends BaseDBTest {

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoPreviewService produtoPreviewService;

    @MockBean
    private ImagemService imagemService;

    @Autowired
    private CommonDao dao;


    @Before
    public void mockImageService(){
        //imagemService = Mockito.spy(imagemService);
        Mockito.when(
                imagemService.getUrlPreviewCliente(Mockito.any(String.class))
        ).thenReturn("http://stc.meuportaretrato.com/img/preview_cliente/1213432142134.png");

        try {
            Mockito.when(imagemService.createPreviewCliente(Mockito.any(String.class),Mockito.any(String.class),
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
     * 1. Adicionar um produto (id=5) para um cliente1
     * 2. Validar se conseguiu sucesso.
     * 3. Adicionar um produto (id=5) para um cliente2
     * 4. Validar se conseguiu sucesso.
     * 5. Adicionar um produto (id=5) para um cliente3
     * 6. Validar se conseguiu sucesso.
     */
    @Test
    public void addCarrinhoFotoSemThread(){

        try{
            PreviewForm form = createItemComFotoCarrinhoFormClienteDinamico(5l);
            ProdutoVo produtoVo = produtoPreviewService.addFoto(form);
            Assert.assertNotNull(produtoVo.getImgPreviewCliente());
            CarrinhoVo vo = carrinhoService.addCarrinho(form.getSessionToken());
            Assert.assertNotNull(vo);
            Assert.assertNotNull(vo.getItems());
            Assert.assertEquals(1, vo.getItems().size());
            vo.getItems().stream().forEach(i -> {
                Assert.assertEquals(new Long(5l), i.getProduto().getId());
                Assert.assertNotNull(i.getAnexos());
                i.getAnexos().stream().forEach(a -> Assert.assertNotNull(a.getUrlFoto()));
            });
            form = createItemComFotoCarrinhoFormClienteDinamico(5l);
            produtoVo = produtoPreviewService.addFoto(form);
            Assert.assertNotNull(produtoVo.getImgPreviewCliente());
            vo = carrinhoService.addCarrinho(form.getSessionToken());
            Assert.assertNotNull(vo);
            Assert.assertNotNull(vo.getItems());
            vo.getItems().stream().forEach(i -> {
                Assert.assertEquals(new Long(5l), i.getProduto().getId());
                Assert.assertNotNull(i.getAnexos());
                i.getAnexos().stream().forEach(a -> Assert.assertNotNull(a.getUrlFoto()));
            });
            form = createItemComFotoCarrinhoFormClienteDinamico(5l);
            produtoVo = produtoPreviewService.addFoto(form);
            Assert.assertNotNull(produtoVo.getImgPreviewCliente());
            vo = carrinhoService.addCarrinho(form.getSessionToken());
            Assert.assertNotNull(vo);
            Assert.assertNotNull(vo.getItems());
            vo.getItems().stream().forEach(i -> {
                Assert.assertEquals(new Long(5l), i.getProduto().getId());
                Assert.assertNotNull(i.getAnexos());
                i.getAnexos().stream().forEach(a -> Assert.assertNotNull(a.getUrlFoto()));
            });

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }


    /**
     * Teste de validacao do carrinho sem foto
     */
    @Test
    public void addCarrinhoSemFoto(){

        PreviewForm form = createItemSemFotoCarrinhoFormClienteDinamico(5l);
        try{
            ProdutoVo produtoVo = produtoPreviewService.addFoto(form);
            Assert.assertNotNull(produtoVo.getImgPreviewCliente());
            carrinhoService.addCarrinho(form.getSessionToken());
            Assert.assertTrue("Nao pode chegar aqui", false);
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),ex.getMessage().contains("Algum anexo está sem foto"));
            try {
                CarrinhoVo carrinhoVo = carrinhoService.getCarrinhoBySessionToken(form.getSessionToken());
                Assert.assertNotNull(carrinhoVo);
                Assert.assertNull("Não pode ter itens",carrinhoVo.getItems());
            }catch (Exception e){
                Assert.assertTrue(e.getMessage(), false);
            }

        }
    }

    /**
     * Teste de validacao do carrinho sem ProdutoPreview
     */
    @Test
    public void addCarrinhoSemProdutoPreview(){

        PreviewForm form = createItemSemFotoCarrinhoFormClienteDinamico(5l);
        try{
            carrinhoService.addCarrinho(form.getSessionToken());
            Assert.assertTrue("Nao pode chegar aqui", false);
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage().contains("Preview do cliente não encontrado"));
            try {
                CarrinhoVo carrinhoVo = carrinhoService.getCarrinhoBySessionToken(form.getSessionToken());
                Assert.assertNotNull(carrinhoVo);
                Assert.assertNull("Não pode ter itens",carrinhoVo.getItems());
            }catch (Exception e){
                Assert.assertTrue(e.getMessage(), false);
            }

        }
    }

    /**
     * 1. Adicionar um produto (id=5) para um cliente1
     * 2. Validar se conseguiu sucesso.
     * 3. Adicionar um produto (id=5) para um cliente2
     * 4. Validar se conseguiu sucesso.
     * 5. Adicionar um produto (id=5) para um cliente3
     * 6. Validar se conseguiu sucesso.
     */
    @Test
    public void addCarrinhoCatalogoSemThread(){

        try{
            PreviewForm form = createItemComCatalogoCarrinhoFormClienteDinamico(5l);
            ProdutoVo produtoVo = produtoPreviewService.addFoto(form);
            Assert.assertNotNull(produtoVo.getImgPreviewCliente());
            CarrinhoVo vo = carrinhoService.addCarrinho(form.getSessionToken());
            Assert.assertNotNull(vo);
            Assert.assertNotNull(vo.getItems());
            Assert.assertEquals(1, vo.getItems().size());
            vo.getItems().stream().forEach(i -> {
                Assert.assertEquals(new Long(5l), i.getProduto().getId());
                Assert.assertNotNull(i.getAnexos());
                i.getAnexos().stream().forEach(a -> {
                    Assert.assertNotNull(a.getUrlFoto());
                    Assert.assertNotNull(a.getIdCatalogo());
                });
            });

            form = createItemComCatalogoCarrinhoFormClienteDinamico(5l);
            produtoVo = produtoPreviewService.addFoto(form);
            Assert.assertNotNull(produtoVo.getImgPreviewCliente());
            vo = carrinhoService.addCarrinho(form.getSessionToken());
            Assert.assertNotNull(vo);
            Assert.assertNotNull(vo.getItems());
            Assert.assertEquals(1, vo.getItems().size());
            vo.getItems().stream().forEach(i -> {
                Assert.assertEquals(new Long(5l), i.getProduto().getId());
                Assert.assertNotNull(i.getAnexos());
                i.getAnexos().stream().forEach(a -> {
                    Assert.assertNotNull(a.getUrlFoto());
                    Assert.assertNotNull(a.getIdCatalogo());
                });
            });


            form = createItemComCatalogoCarrinhoFormClienteDinamico(5l);
            produtoVo = produtoPreviewService.addFoto(form);
            Assert.assertNotNull(produtoVo.getImgPreviewCliente());
            vo = carrinhoService.addCarrinho(form.getSessionToken());
            Assert.assertNotNull(vo);
            Assert.assertNotNull(vo.getItems());
            Assert.assertEquals(1, vo.getItems().size());
            vo.getItems().stream().forEach(i -> {
                Assert.assertEquals(new Long(5l), i.getProduto().getId());
                Assert.assertNotNull(i.getAnexos());
                i.getAnexos().stream().forEach(a -> {
                    Assert.assertNotNull(a.getUrlFoto());
                    Assert.assertNotNull(a.getIdCatalogo());
                });
            });


        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }
    }


    /**
     * 1. Validar se existem 13 itens no estoque.
     * 2. Criar 20 cliente que irao adicionar o mesmo produto(id=4) simultanemaente.
     * 3. Validar se apenas 13 clientes conseguiram adicionar o produto no carrinho
     * 4. Validar se 7 cliente receberam o erro "Infelizmente"
     */
    @Test
    public void addCarrinho() {
        List<EstoqueItemEntity> estoques = dao.findByProperties(EstoqueItemEntity.class,
                new String[]{"idProduto"},
                new Object[]{4l});

        Assert.assertEquals("Está faltando itens no estoque.",13, estoques.size());

        ClienteCarrinhoThreadMonitor monitor = new ClienteCarrinhoThreadMonitor();
        for (int i = 0; i < 20; i++){
            try {
                PreviewForm form = this.createItemComFotoCarrinhoFormClienteDinamico(4l);
                ProdutoVo produtoVo = produtoPreviewService.addFoto(form);
                Assert.assertNotNull(produtoVo.getImgPreviewCliente());
                monitor.addThread(carrinhoService, form);
            } catch (Exception e) {
                Assert.assertTrue(e.getMessage(), false);
            }
        }

        monitor.start();

        while (monitor.isAlive()){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Assert.assertEquals(13,monitor.getQuantidadeSucesso());
        Assert.assertEquals(7,monitor.getQuantidadeError());

    }


    private PreviewForm createItemComFotoCarrinhoFormClienteDinamico(Long idProduto) {
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


    private PreviewForm createItemComFotoCarrinhoFormClienteCadastrado(Long idProduto) {
        PreviewForm previewForm = new PreviewForm();
        previewForm.setIdProduto(idProduto);
        previewForm.setIdCliente(1l);
        previewForm.setAnexos(new ArrayList<>());
        AnexoVo anexoVo = new AnexoVo();
        anexoVo.setFoto(new byte[]{0,0,0,0,0});
        anexoVo.setNomeArquivo(StringUtils.createRandomHash() + ".png");
        previewForm.getAnexos().add(anexoVo);
        return previewForm;
    }

    private PreviewForm createItemSemFotoCarrinhoFormClienteDinamico(Long idProduto) {
        SessionEntity sessionEntity = sessionService.createSession();
        PreviewForm previewForm = new PreviewForm();
        previewForm.setIdProduto(idProduto);
        previewForm.setSessionToken(sessionEntity.getSessionToken());
        previewForm.setAnexos(new ArrayList<>());
        AnexoVo anexoVo = new AnexoVo();
        anexoVo.setNomeArquivo(StringUtils.createRandomHash() + ".png");
        previewForm.getAnexos().add(anexoVo);
        return previewForm;
    }

    private PreviewForm createItemComCatalogoCarrinhoFormClienteDinamico(Long idProduto) {
        SessionEntity sessionEntity = sessionService.createSession();
        PreviewForm previewForm = new PreviewForm();
        previewForm.setIdProduto(idProduto);
        previewForm.setSessionToken(sessionEntity.getSessionToken());
        previewForm.setAnexos(new ArrayList<>());
        AnexoVo anexoVo = new AnexoVo();
        anexoVo.setIdCatalogo(1l);
        previewForm.getAnexos().add(anexoVo);
        return previewForm;
    }


    /**
     * 1. Adicionar dois produtos no carrinho de um cliente
     * 2, Buscar o carrinho do cliente e validar todos os campos.
     */
    @Test
    public void getCarrinho() {

        try {
            PreviewForm item1 = createItemComFotoCarrinhoFormClienteDinamico(5l);
            ProdutoVo produtoVo1 = produtoPreviewService.addFoto(item1);
            Assert.assertNotNull(produtoVo1.getImgPreviewCliente());
            this.carrinhoService.addCarrinho(item1.getSessionToken());
            PreviewForm item2 = createItemComFotoCarrinhoFormClienteDinamico(5l);
            item2.setSessionToken(item1.getSessionToken());
            ProdutoVo produtoVo2 = produtoPreviewService.addFoto(item2);
            Assert.assertNotNull(produtoVo2.getImgPreviewCliente());
            this.carrinhoService.addCarrinho(item2.getSessionToken());


            CarrinhoVo carrinhoVo = this.carrinhoService.getCarrinhoBySessionToken(item1.getSessionToken());
            Assert.assertNotNull(carrinhoVo);
            Assert.assertNotNull(carrinhoVo.getIdCarrinho());
            Assert.assertEquals(item1.getSessionToken(),carrinhoVo.getSessionToken());
            Assert.assertNotNull(carrinhoVo.getItems());
            Assert.assertEquals(2, carrinhoVo.getItems().size());
            for (ItemCarrinhoVo i : carrinhoVo.getItems()){
                Assert.assertNotNull(i.getId());
                Assert.assertNotNull(i.getIdCarrinho());
                Assert.assertNotNull(i.getProduto());
                Assert.assertNotNull(i.getProduto().getId());
                Assert.assertNotNull(i.getProduto().getDescricao());
                Assert.assertNotNull(i.getProduto().getImgPreview());
                Assert.assertNotNull(i.getProduto().getImgPreviewCliente());
                Assert.assertNotNull(i.getAnexos().get(0).getUrlFoto());
            }


        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(),false);
        }


    }



    /**
     * 1. Adicionar dois produtos no carrinho de um cliente
     * 2, Buscar o carrinho do cliente e validar todos os campos.
     */
    @Test
    public void getCarrinhoClienteCadastrado() {

        try {
            PreviewForm item1 = createItemComFotoCarrinhoFormClienteCadastrado(5l);
            ProdutoVo produtoVo1 = produtoPreviewService.addFoto(item1);
            Assert.assertNotNull(produtoVo1.getImgPreviewCliente());
            this.carrinhoService.addCarrinho(item1.getIdCliente());
            PreviewForm item2 = createItemComFotoCarrinhoFormClienteCadastrado(5l);
            ProdutoVo produtoVo2 = produtoPreviewService.addFoto(item2);
            Assert.assertNotNull(produtoVo2.getImgPreviewCliente());
            this.carrinhoService.addCarrinho(item2.getIdCliente());


            CarrinhoVo carrinhoVo = this.carrinhoService.getCarrinhoByIdCliente(item1.getIdCliente());
            Assert.assertNotNull(carrinhoVo);
            Assert.assertNotNull(carrinhoVo.getIdCarrinho());
            Assert.assertEquals(item1.getIdCliente(),carrinhoVo.getIdCliente());
            Assert.assertNotNull(carrinhoVo.getItems());
            Assert.assertEquals(2, carrinhoVo.getItems().size());
            for (ItemCarrinhoVo i : carrinhoVo.getItems()){
                Assert.assertNotNull(i.getId());
                Assert.assertNotNull(i.getIdCarrinho());
                Assert.assertNotNull(i.getProduto());
                Assert.assertNotNull(i.getProduto().getId());
                Assert.assertNotNull(i.getProduto().getDescricao());
                Assert.assertNotNull(i.getProduto().getImgPreview());
                Assert.assertNotNull(i.getProduto().getImgPreviewCliente());
                Assert.assertNotNull(i.getAnexos().get(0).getUrlFoto());
            }
            Assert.assertNotNull(carrinhoVo.getResultFrete());
            Assert.assertTrue(carrinhoVo.getResultFrete().getValor() > 0);

            for (ItemCarrinhoVo item : carrinhoVo.getItems()){
                this.carrinhoService.removeItem(item.getId(),item1.getIdCliente());
            }

        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(),false);
        }


    }


    /**
     * 1. Adicionar dois produtos no carrinho de um cliente
     * 2, Buscar o carrinho do cliente e validar todos os campos.
     */
    @Test
    public void getNovoCarrinho() {
        SessionEntity sessionEntity = sessionService.createSession();
        try {
            CarrinhoVo carrinhoVo = this.carrinhoService.getCarrinhoBySessionToken(sessionEntity.getSessionToken());
            Assert.assertNotNull(carrinhoVo);
            Assert.assertNull(carrinhoVo.getIdCarrinho());
            Assert.assertEquals(sessionEntity.getSessionToken(),carrinhoVo.getSessionToken());
        } catch (CarrinhoServiceException e) {
            e.printStackTrace();
        }
    }


    /**
     * 1. Adicionando o ultimo produto(id=6) da base de teste para o Cliente1.
     * 2. Tentar adicionar o mesmo produto (id=6) para o Cliente2, e esperar um erro "Infelizmente..."
     * 3. Remover o item do Cliente1
     * 4. Tentar adicionar o mesmo produto (id=6) para o Cliente e esperar um sucesso.
     */
    @Test
    public void removeItem() {

        try {
            //ADICIONEI O ULTIMO PRODUTO QUE ESTÁ NA TABELA DE TESTE
            PreviewForm item1 = createItemComFotoCarrinhoFormClienteDinamico(6l);
            ProdutoVo produtoVo1 = produtoPreviewService.addFoto(item1);
            Assert.assertNotNull(produtoVo1.getImgPreviewCliente());
            CarrinhoVo carrinhoVo = this.carrinhoService.addCarrinho(item1.getSessionToken());
            Assert.assertNotNull(carrinhoVo);
            Assert.assertNotNull(carrinhoVo.getItems());
            Assert.assertEquals(1, carrinhoVo.getItems().size());


            PreviewForm outroClienteitem1 = createItemComFotoCarrinhoFormClienteDinamico(6l);
            try{
                ProdutoVo produtoVo2 = produtoPreviewService.addFoto(outroClienteitem1);
                Assert.assertNotNull(produtoVo2.getImgPreviewCliente());
                //TENTANDO ADICIONAR O MESMO PRODUTO PARA OUTRO CLIENTE E ESPERANDO O ERRO.
                CarrinhoVo carr1 = this.carrinhoService.addCarrinho(outroClienteitem1.getSessionToken());
                Assert.assertTrue("Nesse passao ele deveria receber um erro de produto esgotado", false);
            } catch (CarrinhoServiceException e) {
                //CONFIRMANDO O ERRO.
                Assert.assertTrue(e.getMessage().contains("Infelizmente"));
            }

            //REMOVENDO O ITEM DO PRIMEIRO CLIENTE.
            CarrinhoVo carrinhoVo1 = this.carrinhoService.removeItem(carrinhoVo.getItems().get(0).getId(), item1.getSessionToken());
            Assert.assertNotNull(carrinhoVo1);
            Assert.assertEquals(item1.getSessionToken(), carrinhoVo1.getSessionToken());
            Assert.assertNotNull(carrinhoVo1.getItems());
            Assert.assertEquals(0, carrinhoVo1.getItems().size());

            //TENTANDO ADICIONAR O ITEM NOVAMENTE PARA O MESMO CLIENTE.
            CarrinhoVo carrinhoOutroCliente = this.carrinhoService.addCarrinho(outroClienteitem1.getSessionToken());
            Assert.assertNotNull(carrinhoOutroCliente);

        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(),false);
        }


    }

    @Test
    public void moveCarrinhoSessionParaCliente(){

        try{

            //CLIENTE NAO LOGADO COM 1 PRODUTO NO CARRINHO
            PreviewForm form = createItemComFotoCarrinhoFormClienteDinamico(5l);
            ProdutoVo produtoVo = produtoPreviewService.addFoto(form);
            Assert.assertNotNull(produtoVo.getImgPreviewCliente());
            CarrinhoVo carClienteNaoLogado = carrinhoService.addCarrinho(form.getSessionToken());
            Assert.assertNotNull(carClienteNaoLogado);
            Assert.assertNotNull(carClienteNaoLogado.getItems());
            Assert.assertEquals(1, carClienteNaoLogado.getItems().size());


            //CLIENTE LOGADO COM 1 PRODUTO NO CARRINHO
            PreviewForm form2 = createItemComFotoCarrinhoFormClienteCadastrado(5l);
            ProdutoVo produtoVo2 = produtoPreviewService.addFoto(form2);
            Assert.assertNotNull(produtoVo2.getImgPreviewCliente());
            CarrinhoVo carClienteLogado = carrinhoService.addCarrinho(form2.getIdCliente());
            Assert.assertNotNull(carClienteLogado);
            Assert.assertNotNull(carClienteLogado.getItems());
            Assert.assertEquals(1, carClienteLogado.getItems().size());

            carrinhoService.moveCarSessionParaCarLogado(form.getSessionToken(),form2.getIdCliente());

            CarrinhoVo carrinhoCliente = carrinhoService.getCarrinhoByIdCliente(form2.getIdCliente());
            Assert.assertNotNull(carrinhoCliente);
            Assert.assertNotNull(carrinhoCliente.getItems());
            Assert.assertEquals(form2.getIdCliente(),carrinhoCliente.getIdCliente());
            Assert.assertTrue(carClienteLogado.getValorItens() < carrinhoCliente.getValorItens());
            Assert.assertEquals(new Integer(2), carrinhoCliente.getTotalItens());
            Assert.assertEquals(2, carrinhoCliente.getItems().size());

            carClienteNaoLogado = carrinhoService.getCarrinhoBySessionToken(form.getSessionToken());
            Assert.assertNotNull(carClienteNaoLogado);
            Assert.assertTrue(carClienteNaoLogado.getTotalItens() == 0);

            carrinhoCliente.getItems().forEach(i -> {
                try {
                    carrinhoService.removeItem(i.getId(),carrinhoCliente.getIdCliente());
                } catch (CarrinhoServiceException e) {
                    Assert.assertTrue(e.getMessage(),false);
                }
            });



        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }
}
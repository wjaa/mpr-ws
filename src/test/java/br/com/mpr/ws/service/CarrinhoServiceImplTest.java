package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.EstoqueItemEntity;
import br.com.mpr.ws.exception.CarrinhoServiceException;
import br.com.mpr.ws.service.thread.ClienteCarrinhoThreadMonitor;
import br.com.mpr.ws.utils.StringUtils;
import br.com.mpr.ws.vo.AnexoVo;
import br.com.mpr.ws.vo.CarrinhoVo;
import br.com.mpr.ws.vo.ItemCarrinhoForm;
import br.com.mpr.ws.vo.ItemCarrinhoVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoServiceImplTest extends BaseDBTest {

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private CommonDao dao;


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
            CarrinhoVo vo = carrinhoService.addCarrinho(createItemComFotoCarrinhoFormClienteDinamico(5l));
            Assert.assertNotNull(vo);
            Assert.assertNotNull(vo.getItems());
            Assert.assertEquals(1, vo.getItems().size());
            vo.getItems().stream().forEach(i -> {
                Assert.assertEquals(new Long(5l), i.getProduto().getId());
                Assert.assertNotNull(i.getAnexos());
                i.getAnexos().stream().forEach(a -> Assert.assertNotNull(a.getUrlFoto()));
            });
            vo = carrinhoService.addCarrinho(createItemComFotoCarrinhoFormClienteDinamico(5l));
            Assert.assertNotNull(vo);
            Assert.assertNotNull(vo.getItems());
            vo.getItems().stream().forEach(i -> {
                Assert.assertEquals(new Long(5l), i.getProduto().getId());
                Assert.assertNotNull(i.getAnexos());
                i.getAnexos().stream().forEach(a -> Assert.assertNotNull(a.getUrlFoto()));
            });
            vo = carrinhoService.addCarrinho(createItemComFotoCarrinhoFormClienteDinamico(5l));
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

        ItemCarrinhoForm item = createItemSemFotoCarrinhoFormClienteDinamico(5l);
        try{
            CarrinhoVo vo = carrinhoService.addCarrinho(item);
            Assert.assertTrue("Nao pode chegar aqui", false);
        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage().contains("uma imagem é obrigatória"));
            CarrinhoVo carrinhoVo = carrinhoService.getCarrinhoByIdCliente(item.getIdCliente());
            Assert.assertNotNull(carrinhoVo);
            Assert.assertNull("Não pode ter itens",carrinhoVo.getItems());

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
            CarrinhoVo vo = carrinhoService.addCarrinho(createItemComCatalogoCarrinhoFormClienteDinamico(5l));
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


            vo = carrinhoService.addCarrinho(createItemComCatalogoCarrinhoFormClienteDinamico(5l));
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


            vo = carrinhoService.addCarrinho(createItemComCatalogoCarrinhoFormClienteDinamico(5l));
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
            monitor.addThread(carrinhoService, this.createItemComFotoCarrinhoFormClienteDinamico(4l));
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


    private ItemCarrinhoForm createItemComFotoCarrinhoFormClienteDinamico(Long idProduto) {
        ItemCarrinhoForm itemCarrinhoForm = new ItemCarrinhoForm();
        itemCarrinhoForm.setIdProduto(idProduto);
        itemCarrinhoForm.setSessionToken(StringUtils.createRandomHash());
        itemCarrinhoForm.setAnexos(new ArrayList<>());
        AnexoVo anexoVo = new AnexoVo();
        anexoVo.setFoto(new byte[]{0,0,0,0,0});
        anexoVo.setNomeArquivo(StringUtils.createRandomHash() + ".png");
        itemCarrinhoForm.getAnexos().add(anexoVo);
        return itemCarrinhoForm;
    }


    private ItemCarrinhoForm createItemComFotoCarrinhoFormClienteCadastrado(Long idProduto) {
        ItemCarrinhoForm itemCarrinhoForm = new ItemCarrinhoForm();
        itemCarrinhoForm.setIdProduto(idProduto);
        itemCarrinhoForm.setIdCliente(1l);
        itemCarrinhoForm.setAnexos(new ArrayList<>());
        AnexoVo anexoVo = new AnexoVo();
        anexoVo.setFoto(new byte[]{0,0,0,0,0});
        anexoVo.setNomeArquivo(StringUtils.createRandomHash() + ".png");
        itemCarrinhoForm.getAnexos().add(anexoVo);
        return itemCarrinhoForm;
    }

    private ItemCarrinhoForm createItemSemFotoCarrinhoFormClienteDinamico(Long idProduto) {
        ItemCarrinhoForm itemCarrinhoForm = new ItemCarrinhoForm();
        itemCarrinhoForm.setIdProduto(idProduto);
        itemCarrinhoForm.setSessionToken(StringUtils.createRandomHash());
        itemCarrinhoForm.setAnexos(new ArrayList<>());
        AnexoVo anexoVo = new AnexoVo();
        anexoVo.setNomeArquivo(StringUtils.createRandomHash() + ".png");
        itemCarrinhoForm.getAnexos().add(anexoVo);
        return itemCarrinhoForm;
    }

    private ItemCarrinhoForm createItemComCatalogoCarrinhoFormClienteDinamico(Long idProduto) {
        ItemCarrinhoForm itemCarrinhoForm = new ItemCarrinhoForm();
        itemCarrinhoForm.setIdProduto(idProduto);
        itemCarrinhoForm.setSessionToken(StringUtils.createRandomHash());
        itemCarrinhoForm.setAnexos(new ArrayList<>());
        AnexoVo anexoVo = new AnexoVo();
        anexoVo.setIdCatalogo(1l);
        itemCarrinhoForm.getAnexos().add(anexoVo);
        return itemCarrinhoForm;
    }


    /**
     * 1. Adicionar dois produtos no carrinho de um cliente
     * 2, Buscar o carrinho do cliente e validar todos os campos.
     */
    @Test
    public void getCarrinho() {

        try {
            ItemCarrinhoForm item1 = createItemComFotoCarrinhoFormClienteDinamico(5l);
            this.carrinhoService.addCarrinho(item1);
            ItemCarrinhoForm item2 = createItemComFotoCarrinhoFormClienteDinamico(5l);
            item2.setSessionToken(item1.getSessionToken());
            this.carrinhoService.addCarrinho(item2);


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
                Assert.assertNotNull(i.getAnexos().get(0).getUrlFoto());
            }


        } catch (CarrinhoServiceException e) {
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
            ItemCarrinhoForm item1 = createItemComFotoCarrinhoFormClienteCadastrado(5l);
            this.carrinhoService.addCarrinho(item1);
            ItemCarrinhoForm item2 = createItemComFotoCarrinhoFormClienteCadastrado(5l);
            this.carrinhoService.addCarrinho(item2);


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
                Assert.assertNotNull(i.getAnexos().get(0).getUrlFoto());
            }
            Assert.assertNotNull(carrinhoVo.getResultFrete());
            Assert.assertTrue(carrinhoVo.getResultFrete().getValor() > 0);

            for (ItemCarrinhoVo item : carrinhoVo.getItems()){
                this.carrinhoService.removeItem(item.getId(),item1.getIdCliente());
            }

        } catch (CarrinhoServiceException e) {
            Assert.assertTrue(e.getMessage(),false);
        }


    }


    /**
     * 1. Adicionar dois produtos no carrinho de um cliente
     * 2, Buscar o carrinho do cliente e validar todos os campos.
     */
    @Test
    public void getNovoCarrinho() {

        CarrinhoVo carrinhoVo = this.carrinhoService.getCarrinhoBySessionToken("XXXXTTTTHHHHHH");
        Assert.assertNotNull(carrinhoVo);
        Assert.assertNull(carrinhoVo.getIdCarrinho());
        Assert.assertEquals("XXXXTTTTHHHHHH",carrinhoVo.getSessionToken());

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
            ItemCarrinhoForm item1 = createItemComFotoCarrinhoFormClienteDinamico(6l);
            CarrinhoVo carrinhoVo = this.carrinhoService.addCarrinho(item1);
            Assert.assertNotNull(carrinhoVo);
            Assert.assertNotNull(carrinhoVo.getItems());
            Assert.assertEquals(1, carrinhoVo.getItems().size());


            ItemCarrinhoForm outroClienteitem1 = createItemComFotoCarrinhoFormClienteDinamico(6l);
            try{
                //TENTANDO ADICIONAR O MESMO PRODUTO PARA OUTRO CLIENTE E ESPERANDO O ERRO.
                CarrinhoVo  carr1 = this.carrinhoService.addCarrinho(outroClienteitem1);
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
            CarrinhoVo carrinhoOutroCliente = this.carrinhoService.addCarrinho(outroClienteitem1);
            Assert.assertNotNull(carrinhoOutroCliente);

        } catch (CarrinhoServiceException e) {
            Assert.assertTrue(e.getMessage(),false);
        }


    }
}
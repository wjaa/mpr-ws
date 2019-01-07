package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.EstoqueItemEntity;
import br.com.mpr.ws.entity.ItemCarrinhoEntity;
import br.com.mpr.ws.exception.CarrinhoServiceException;
import br.com.mpr.ws.service.thread.ClienteCarrinhoThread;
import br.com.mpr.ws.service.thread.ClienteCarrinhoThreadMonitor;
import br.com.mpr.ws.utils.StringUtils;
import br.com.mpr.ws.vo.CarrinhoVo;
import br.com.mpr.ws.vo.ItemCarrinhoForm;
import br.com.mpr.ws.vo.ItemCarrinhoVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

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
    public void addCarrinhoSemThread(){

        try{
            CarrinhoVo vo = carrinhoService.addCarrinho(createItemCarrinhoFormClienteDinamico(5l));
            Assert.assertNotNull(vo);
            vo = carrinhoService.addCarrinho(createItemCarrinhoFormClienteDinamico(5l));
            Assert.assertNotNull(vo);
            vo = carrinhoService.addCarrinho(createItemCarrinhoFormClienteDinamico(5l));
            Assert.assertNotNull(vo);

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage().contains("Infelizmente"));
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
            monitor.addThread(carrinhoService, this.createItemCarrinhoFormClienteDinamico(4l));
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

    private ItemCarrinhoForm createItemCarrinhoFormClienteDinamico(Long idProduto) {
        ItemCarrinhoForm itemCarrinhoForm = new ItemCarrinhoForm();
        itemCarrinhoForm.setIdProduto(idProduto);
        itemCarrinhoForm.setKeyDevice(StringUtils.createRandomHash());
        itemCarrinhoForm.setFoto(new byte[]{0,0,0,0,0});
        itemCarrinhoForm.setNomeArquivo(StringUtils.createRandomHash() + ".png");
        return itemCarrinhoForm;
    }


    /**
     * 1. Adicionar dois produtos no carrinho de um cliente
     * 2, Buscar o carrinho do cliente e validar todos os campos.
     */
    @Test
    public void getCarrinho() {

        try {
            ItemCarrinhoForm item1 = createItemCarrinhoFormClienteDinamico(5l);
            this.carrinhoService.addCarrinho(item1);
            ItemCarrinhoForm item2 = createItemCarrinhoFormClienteDinamico(5l);
            item2.setKeyDevice(item1.getKeyDevice());
            this.carrinhoService.addCarrinho(item2);


            CarrinhoVo carrinhoVo = this.carrinhoService.getCarrinhoByKeyDevice(item1.getKeyDevice());
            Assert.assertNotNull(carrinhoVo);
            Assert.assertNotNull(carrinhoVo.getIdCarrinho());
            Assert.assertEquals(item1.getKeyDevice(),carrinhoVo.getKeyDevice());
            Assert.assertNotNull(carrinhoVo.getItems());
            Assert.assertEquals(2, carrinhoVo.getItems().size());
            for (ItemCarrinhoVo i : carrinhoVo.getItems()){
                Assert.assertNotNull(i.getId());
                Assert.assertNotNull(i.getIdCarrinho());
                Assert.assertNotNull(i.getProduto());
                Assert.assertNotNull(i.getProduto().getId());
                Assert.assertNotNull(i.getProduto().getDescricao());
                Assert.assertNotNull(i.getProduto().getImgPreview());
                Assert.assertNotNull(i.getUrlFoto());
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

        CarrinhoVo carrinhoVo = this.carrinhoService.getCarrinhoByKeyDevice("XXXXTTTTHHHHHH");
        Assert.assertNotNull(carrinhoVo);
        Assert.assertNull(carrinhoVo.getIdCarrinho());
        Assert.assertEquals("XXXXTTTTHHHHHH",carrinhoVo.getKeyDevice());

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
            ItemCarrinhoForm item1 = createItemCarrinhoFormClienteDinamico(6l);
            CarrinhoVo carrinhoVo = this.carrinhoService.addCarrinho(item1);
            Assert.assertNotNull(carrinhoVo);
            Assert.assertNotNull(carrinhoVo.getItems());
            Assert.assertEquals(1, carrinhoVo.getItems().size());


            ItemCarrinhoForm outroClienteitem1 = createItemCarrinhoFormClienteDinamico(6l);
            try{
                //TENTANDO ADICIONAR O MESMO PRODUTO PARA OUTRO CLIENTE E ESPERANDO O ERRO.
                CarrinhoVo  carr1 = this.carrinhoService.addCarrinho(outroClienteitem1);
                Assert.assertTrue("Nesse passao ele deveria receber um erro de produto esgotado", false);
            } catch (CarrinhoServiceException e) {
                //CONFIRMANDO O ERRO.
                Assert.assertTrue(e.getMessage().contains("Infelizmente"));
            }

            //REMOVENDO O ITEM DO PRIMEIRO CLIENTE.
            CarrinhoVo carrinhoVo1 = this.carrinhoService.removeItem(carrinhoVo.getItems().get(0).getId());
            Assert.assertNotNull(carrinhoVo1);
            Assert.assertEquals(item1.getKeyDevice(), carrinhoVo1.getKeyDevice());
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
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


    @Test
    public void addCarrinhoSemThread() throws CarrinhoServiceException {

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


    @Test
    public void addCarrinho() {
        List<EstoqueItemEntity> estoques = dao.findByProperties(EstoqueItemEntity.class,
                new String[]{"idProduto"},
                new Object[]{4l});

        Assert.assertEquals(13, estoques.size());

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
            System.out.println("waiting....");
        }

        Assert.assertEquals(13,monitor.getQuantidadeSucesso());
        Assert.assertEquals(7,monitor.getQuantidadeError());

    }

    private ItemCarrinhoForm createItemCarrinhoFormClienteDinamico(Long idProduto) {
        ItemCarrinhoForm itemCarrinhoForm = new ItemCarrinhoForm();
        itemCarrinhoForm.setIdProduto(idProduto);
        itemCarrinhoForm.setKeyDevice(StringUtils.createRandomHash());
        return itemCarrinhoForm;
    }

    @Test
    public void getCarrinho() {
    }

    @Test
    public void removeItem() {
    }
}
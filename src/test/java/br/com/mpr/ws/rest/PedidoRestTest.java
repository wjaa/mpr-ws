package br.com.mpr.ws.rest;

import br.com.mpr.ws.BaseMvcTest;
import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.helper.PedidoHelper;
import br.com.mpr.ws.service.PedidoService;
import br.com.mpr.ws.utils.ObjectUtils;
import br.com.mpr.ws.vo.CheckoutForm;
import org.assertj.core.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

public class PedidoRestTest extends BaseMvcTest {


    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoHelper pedidoHelper;


    @Test
    public void findById() {
        try{
            CheckoutForm form = pedidoHelper.createCheckoutForm();

            PedidoEntity pedido = pedidoService.createPedido(form);


            ResultActions ra = getMvcGetResultActions("/api/v1/core/pedido/findById/" + pedido.getId());
            String resultJson = ra.andReturn().getResponse().getContentAsString();
            PedidoEntity findPedido = ObjectUtils.fromJSON(resultJson, PedidoEntity.class);

            Assert.assertNotNull(findPedido);
            Assert.assertEquals(findPedido,pedido);
            Assert.assertNotNull(findPedido.getStatusAtual());
            Assert.assertNotNull(findPedido.getItens());


        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }

    @Test
    public void findByIdCliente() {
        try{
            CheckoutForm form1 = pedidoHelper.createCheckoutForm();
            PedidoEntity pedido1 = pedidoService.createPedido(form1);

            CheckoutForm form2 = pedidoHelper.createCheckoutForm();
            PedidoEntity pedido2 = pedidoService.createPedido(form2);

            ResultActions ra = getMvcGetResultActions("/api/v1/core/pedido/findByIdCliente/" + PedidoHelper.ID_CLIENTE);
            String resultJson = ra.andReturn().getResponse().getContentAsString();
            List<PedidoEntity> resultList = Arrays.nonNullElementsIn(ObjectUtils.fromJSON(resultJson, PedidoEntity[].class));

            for (PedidoEntity findPedido : resultList){
                Assert.assertNotNull(findPedido);

                if (findPedido.getId() == pedido1.getId()){
                    Assert.assertEquals(findPedido,pedido1);
                }
                if (findPedido.getId() == pedido2.getId()){
                    Assert.assertEquals(findPedido,pedido2);
                }
                Assert.assertNotNull(findPedido.getStatusAtual());
                Assert.assertNotNull(findPedido.getItens());
            }



        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }

    @Test
    public void findByCodigo() {
        try{
            CheckoutForm form = pedidoHelper.createCheckoutForm();

            PedidoEntity pedido = pedidoService.createPedido(form);

            ResultActions ra = getMvcGetResultActions("/api/v1/core/pedido/findByCodigo/" + pedido.getCodigoPedido());
            String resultJson = ra.andReturn().getResponse().getContentAsString();
            PedidoEntity findPedido = ObjectUtils.fromJSON(resultJson, PedidoEntity.class);

            Assert.assertNotNull(findPedido);
            Assert.assertEquals(findPedido,pedido);
            Assert.assertNotNull(findPedido.getStatusAtual());
            Assert.assertNotNull(findPedido.getItens());


        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }
}
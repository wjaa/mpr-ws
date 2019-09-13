package br.com.mpr.ws.rest;

import br.com.mpr.ws.BaseMvcTest;
import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.exception.ImagemServiceException;
import br.com.mpr.ws.helper.PedidoHelper;
import br.com.mpr.ws.service.ImagemService;
import br.com.mpr.ws.service.PedidoService;
import br.com.mpr.ws.utils.ObjectUtils;
import br.com.mpr.ws.vo.CheckoutForm;
import org.assertj.core.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

public class PedidoRestTest extends BaseMvcTest {


    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoHelper pedidoHelper;

    @MockBean
    private ImagemService imagemService;

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


    @Test
    public void findById() {
        try{

            String token = obtainAccessTokenPassword();

            CheckoutForm form = pedidoHelper.createCheckoutFormCC();

            PedidoEntity pedido = pedidoService.createPedido(form);


            ResultActions ra = getMvcGetResultActions("/api/v1/core/pedido/findById/" + pedido.getId(),
                    token);
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
            String token = obtainAccessTokenPassword();

            CheckoutForm form1 = pedidoHelper.createCheckoutFormCC();
            PedidoEntity pedido1 = pedidoService.createPedido(form1);

            CheckoutForm form2 = pedidoHelper.createCheckoutFormCC();
            PedidoEntity pedido2 = pedidoService.createPedido(form2);

            ResultActions ra = getMvcGetResultActions("/api/v1/core/pedido/list",token);
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

            String token = obtainAccessTokenPassword();
            CheckoutForm form = pedidoHelper.createCheckoutFormCC();

            PedidoEntity pedido = pedidoService.createPedido(form);

            ResultActions ra = getMvcGetResultActions("/api/v1/core/pedido/findByCodigo/" + pedido.getCodigoPedido(),
                    token);
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

    protected AppUser getAppUser(){
        return getAppUserClient();
    }
}
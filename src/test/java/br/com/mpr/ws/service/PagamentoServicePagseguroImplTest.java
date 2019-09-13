package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.entity.PagamentoType;
import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.entity.StatusPedidoEntity;
import br.com.mpr.ws.exception.ImagemServiceException;
import br.com.mpr.ws.exception.RestException;
import br.com.mpr.ws.helper.PedidoHelper;
import br.com.mpr.ws.mock.TransactionDetailMock;
import br.com.mpr.ws.utils.RestUtils;
import br.com.mpr.ws.vo.*;
import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.common.domain.*;
import br.com.uol.pagseguro.api.common.domain.builder.CreditCardBuilder;
import br.com.uol.pagseguro.api.transaction.TransactionsResource;
import br.com.uol.pagseguro.api.transaction.register.DirectPaymentRegisterResource;
import br.com.uol.pagseguro.api.transaction.register.DirectPaymentRegistration;
import br.com.uol.pagseguro.api.transaction.register.DirectPaymentRegistrationBuilder;
import br.com.uol.pagseguro.api.transaction.search.TransactionDetail;
import br.com.uol.pagseguro.api.transaction.search.TransactionDetailXML;
import br.com.uol.pagseguro.api.utils.Builder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wagner on 21/01/19.
 */
public class PagamentoServicePagseguroImplTest extends BaseDBTest {

    @Autowired
    @Qualifier("PagamentoServicePagseguroMock")
    private PagamentoService pagamentoService;

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private PedidoHelper pedidoHelper;

    @MockBean
    private ImagemService imagemService;

    @Autowired
    private PedidoService pedidoService;


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


    @Before
    public void preparaPagseguro(){
        PagSeguro pagSeguro = Mockito.mock(PagSeguro.class);
        ((PagamentoServicePagseguroMock)pagamentoService)
                .setPagSeguroMock(pagSeguro);

        TransactionsResource tr = Mockito.mock(TransactionsResource.class);
        DirectPaymentRegisterResource directPayment = Mockito.mock(DirectPaymentRegisterResource.class);

        Mockito.when(pagSeguro.transactions()).thenReturn(tr);
        Mockito.when(tr.register(Mockito.any(DirectPaymentRegistrationBuilder.class))).thenReturn(directPayment);
        Mockito.when(directPayment.withBankSlip()).thenReturn(new TransactionDetailMock());
        Mockito.when(directPayment.withCreditCard(Mockito.any(CreditCardBuilder.class))).thenReturn(new TransactionDetailMock());
    }


    @Test
    public void pagamentoBoleto() throws Exception {
        CheckoutForm form = pedidoHelper.createCheckoutFormBoleto();
        ResultadoPagamentoVo result = pagamentoService.pagamento(form);
        Assert.assertNotNull(result);
        PedidoEntity pedidoEntity = pedidoService.getPedido(result.getIdPedido());
        Assert.assertEquals(pedidoEntity.getCodigoPedido(), result.getCodigoPedido());
        Assert.assertEquals(pedidoEntity.getData(), result.getDataPedido());
        Assert.assertEquals(pedidoEntity.getUrlBoleto(), result.getPaymentLink());
        Assert.assertEquals(pedidoEntity.getStatusAtual().getNomeCliente(), result.getStatusPedido());
        Assert.assertEquals("Pedido recebido (aguardando pgto).", result.getStatusPedido());
        Assert.assertEquals(pedidoEntity.getPagamentoType(),result.getPagamentoType());
        Assert.assertEquals(PagamentoType.BOLETO, result.getPagamentoType());
    }

    @Test
    public void pagamentoCartao() throws Exception {
        CheckoutForm form = pedidoHelper.createCheckoutFormCC();
        ResultadoPagamentoVo result = pagamentoService.pagamento(form);
        Assert.assertNotNull(result);
        PedidoEntity pedidoEntity = pedidoService.getPedido(result.getIdPedido());
        Assert.assertEquals(pedidoEntity.getCodigoPedido(), result.getCodigoPedido());
        Assert.assertEquals(pedidoEntity.getData(), result.getDataPedido());
        Assert.assertEquals(pedidoEntity.getStatusAtual().getNomeCliente(), result.getStatusPedido());
        Assert.assertEquals("Pedido recebido (aguardando pgto).", result.getStatusPedido());
        Assert.assertEquals(pedidoEntity.getPagamentoType(),result.getPagamentoType());
        Assert.assertEquals(PagamentoType.CARTAO_CREDITO, result.getPagamentoType());
    }




}
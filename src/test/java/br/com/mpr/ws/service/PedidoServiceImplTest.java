package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.entity.*;
import br.com.mpr.ws.exception.CarrinhoServiceException;
import br.com.mpr.ws.exception.CheckoutServiceException;
import br.com.mpr.ws.exception.PedidoServiceException;
import br.com.mpr.ws.helper.PedidoHelper;
import br.com.mpr.ws.utils.DateUtils;
import br.com.mpr.ws.vo.*;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by wagner on 17/02/19.
 */
public class PedidoServiceImplTest extends BaseDBTest {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private PedidoHelper pedidoHelper;

    @Test
    public void testCreatePedido() {
        try {
            EstoqueEntity estoque = adminService.listEstoqueByIdProduto(2l).get(0);
            Assert.assertEquals(new Integer(4), estoque.getQuantidadeAtual());
            CheckoutVo checkout = this.checkoutService.checkout(1l);
            CheckoutForm checkoutForm = new CheckoutForm();
            FormaPagamentoVo formaPagamentoVo = new FormaPagamentoVo();
            formaPagamentoVo.setPagamentoType(PagamentoType.CARTAO_CREDITO);
            checkoutForm.setFormaPagamento(formaPagamentoVo);
            checkoutForm.setIdCheckout(1l);
            PedidoEntity pedido = pedidoService.createPedido(checkoutForm);
            Assert.assertNotNull(pedido);
            Assert.assertNotNull(pedido.getCodigoPedido());
            Assert.assertNotNull(pedido.getId());
            Assert.assertEquals("START", pedido.getCodigoTransacao());
            Assert.assertEquals(DateUtils.formatddMMyyyy(new Date()), DateUtils.formatddMMyyyy(pedido.getData()));
            Assert.assertNotNull(pedido.getDataEntrega());
            Assert.assertNull(pedido.getPagamentoType());
            Assert.assertNotNull(pedido.getIdEndereco());
            Assert.assertNull(pedido.getStatusAtual());
            Assert.assertNotNull(pedido.getIdCliente());
            Assert.assertEquals(FreteType.ECONOMICO, pedido.getTipoFrete());
            Assert.assertNotNull(pedido.getValorProdutos());
            Assert.assertEquals(new Double(28.50), pedido.getValorProdutos());
            Assert.assertEquals(new Double(18.50), pedido.getValorFrete());
            Assert.assertEquals(DateUtils.formatddMMyyyy(DateUtils.addDays(new Date(), 8)),
                    DateUtils.formatddMMyyyy(pedido.getDataEntrega()));
            Assert.assertTrue(pedido.getValorTotal() > pedido.getValorProdutos());
            Assert.assertEquals(new Double(0.0), pedido.getValorDesconto());
            estoque = adminService.listEstoqueByIdProduto(2l).get(0);
            Assert.assertEquals(new Integer(4), estoque.getQuantidadeAtual());


        } catch (PedidoServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        } catch (CheckoutServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        }
    }

    @Test
    public void testCancelarPedido() {
        try {

            EstoqueEntity estoque = adminService.listEstoqueByIdProduto(4l).get(0);
            Assert.assertTrue(estoque.getQuantidadeAtual() > 0);
            Integer quantidadeInicial = estoque.getQuantidadeAtual();
            CheckoutForm checkoutForm = pedidoHelper.createCheckoutForm();
            PedidoEntity pedido = pedidoService.createPedido(checkoutForm);
            estoque = adminService.listEstoqueByIdProduto(4l).get(0);
            //retirando um item do estoque
            Assert.assertEquals(new Integer(quantidadeInicial - 1), estoque.getQuantidadeAtual());

            pedido = pedidoService.cancelarPedido(pedido.getId());

            Assert.assertNotNull(pedido);
            Assert.assertEquals(SysCodeType.CACL, pedido.getStatusAtual().getSyscode());
            estoque = adminService.listEstoqueByIdProduto(4l).get(0);

            //produto precisa voltar para o estoque
            Assert.assertEquals(new Integer(quantidadeInicial), estoque.getQuantidadeAtual());

        } catch (PedidoServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        } catch (CheckoutServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        } catch (CarrinhoServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        }
    }



    @Test
    public void testConfirmarPedido() {

        try {
            CheckoutForm checkoutForm = pedidoHelper.createCheckoutForm();
            PedidoEntity pedido = pedidoService.createPedido(checkoutForm);
            pedido = pedidoService.confirmarPedido("XXXYYYZZZ", pedido.getId());

            Assert.assertEquals("XXXYYYZZZ", pedido.getCodigoTransacao());
            Assert.assertEquals(SysCodeType.AGPG, pedido.getStatusAtual().getSyscode());

        } catch (CarrinhoServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        } catch (CheckoutServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        } catch (PedidoServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        }

    }

    @Test
    public void testCreateNovoHistorico() {
        try {
            CheckoutForm checkoutForm = pedidoHelper.createCheckoutForm();
            PedidoEntity pedido = pedidoService.createPedido(checkoutForm);
            pedido = pedidoService.confirmarPedido("AAABBBCCC", pedido.getId());
            pedidoService.createNovoHistorico(pedido.getId(), SysCodeType.PGCF);
            pedido = pedidoService.getPedido(pedido.getId());

            Assert.assertEquals("AAABBBCCC", pedido.getCodigoTransacao());
            Assert.assertEquals(SysCodeType.PGCF, pedido.getStatusAtual().getSyscode());

        } catch (CarrinhoServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        } catch (CheckoutServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        } catch (PedidoServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        }
    }

    @Test
    public void testFindPedidoByCodigo() {
        try {
            CheckoutForm checkoutForm = pedidoHelper.createCheckoutForm();
            PedidoEntity pedido = pedidoService.createPedido(checkoutForm);
            PedidoEntity findPedido = pedidoService.findPedidoByCodigo(pedido.getCodigoPedido());
            Assert.assertNotNull(findPedido);
            Assert.assertEquals(findPedido.getCodigoPedido(), pedido.getCodigoPedido());

        } catch (CarrinhoServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        } catch (CheckoutServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        } catch (PedidoServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        }
    }

    @Test
    public void testFindPedidoByStatus() {
        try {
            CheckoutForm checkoutForm = pedidoHelper.createCheckoutForm();
            PedidoEntity pedido = pedidoService.createPedido(checkoutForm);
            List<PedidoEntity> listPedidos = pedidoService.findPedidoByStatus(SysCodeType.PECR);
            Assert.assertNotNull(listPedidos);
            Assert.assertTrue(listPedidos.size() > 0);


            //PRECISA ACHAR O PEDIDO COM STATUS DE PEDIDO CRIADO
            boolean achouPedido = false;
            for (PedidoEntity p : listPedidos) {
                if (p.getId().equals(pedido.getId())) {
                    achouPedido = true;
                }
            }
            Assert.assertTrue(achouPedido);

            // NAO PODE ACHAR O PEDIDO COM STATUS DE AGUARDANDO PAGAMENTO.
            listPedidos = pedidoService.findPedidoByStatus(SysCodeType.AGPG);
            Assert.assertNotNull(listPedidos);
            achouPedido = false;
            for (PedidoEntity p : listPedidos) {
                if (p.getId().equals(pedido.getId())) {
                    achouPedido = true;
                }
            }
            Assert.assertFalse(achouPedido);

        } catch (CarrinhoServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        } catch (CheckoutServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        } catch (PedidoServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        }
    }

    @Test
    public void testFindPedidoByTransactionCode() {
        try {
            CheckoutForm checkoutForm = pedidoHelper.createCheckoutForm();
            PedidoEntity pedido = pedidoService.createPedido(checkoutForm);
            pedido = pedidoService.confirmarPedido("JJJ666888", pedido.getId());
            Assert.assertNotNull(pedido);
            PedidoEntity findPedido = pedidoService.findPedidoByTransactionCode("JJJ666888");
            Assert.assertEquals(findPedido.getCodigoPedido(), pedido.getCodigoPedido());
            Assert.assertEquals(findPedido.getId(), pedido.getId());
            Assert.assertEquals("JJJ666888", findPedido.getCodigoTransacao());

        } catch (CarrinhoServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        } catch (CheckoutServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        } catch (PedidoServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        }
    }

    @Test
    public void testGetPedido() {
        try {
            CheckoutForm checkoutForm = pedidoHelper.createCheckoutForm();
            PedidoEntity pedido = pedidoService.createPedido(checkoutForm);
            PedidoEntity findPedido = pedidoService.getPedido(pedido.getId());
            Assert.assertNotNull(findPedido);
            Assert.assertEquals(findPedido.getCodigoPedido(), pedido.getCodigoPedido());
            Assert.assertEquals(findPedido.getId(), pedido.getId());
        } catch (CarrinhoServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        } catch (CheckoutServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        } catch (PedidoServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        }
    }

    @Test
    public void testFindPedidoByIdCliente() {
        try {
            CheckoutForm checkoutForm = pedidoHelper.createCheckoutForm();
            PedidoEntity pedido = pedidoService.createPedido(checkoutForm);
            List<PedidoEntity> listPedidos = pedidoService.findPedidoByIdCliente(1l);
            Assert.assertNotNull(listPedidos);
            Assert.assertTrue(listPedidos.size() > 0);
            for (PedidoEntity p : listPedidos) {
                Assert.assertEquals(new Long(1l), p.getIdCliente());
            }

        } catch (PedidoServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        } catch (CarrinhoServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        } catch (CheckoutServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        }


    }
}
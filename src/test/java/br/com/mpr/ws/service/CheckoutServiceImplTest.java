package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.entity.FreteType;
import br.com.mpr.ws.exception.CheckoutServiceException;
import br.com.mpr.ws.vo.CheckoutVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wagner on 11/01/19.
 */
public class CheckoutServiceImplTest extends BaseDBTest {

    @Autowired
    private CheckoutService checkoutService;

    @Test
    public void testCheckout(){

        try {
            CheckoutVo checkout = this.checkoutService.checkout(1l);
            Assert.assertEquals(new Double(28.50), checkout.getValorProdutos());
            Assert.assertTrue(checkout.getValorFrete() > 0);
            Assert.assertTrue(checkout.getValorTotal() > checkout.getValorProdutos());
            Assert.assertEquals(new Double(0.0),checkout.getValorDesconto());
            Assert.assertNotNull(checkout.getCarrinho().getItems());
            Assert.assertEquals(1, checkout.getCarrinho().getItems().size());
            Assert.assertNotNull(checkout.getEndereco());
            Assert.assertNotNull(checkout.getEndereco().getEndereco());
            Assert.assertNotNull(checkout.getEndereco().getDescricao());
            Assert.assertNotNull(checkout.getValorFrete());
            Assert.assertNotNull(checkout.getListResultFrete());
            Assert.assertEquals(2, checkout.getListResultFrete().size());
            Assert.assertEquals(FreteType.ECONOMICO, checkout.getFreteSelecionado().getFreteType());
            Assert.assertEquals(checkout.getValorFrete(), checkout.getFreteSelecionado().getValor());
            Assert.assertEquals(checkout.getDiasEntrega(), checkout.getFreteSelecionado().getDiasUteis());
            Assert.assertNotNull(checkout.getCheckoutToken());
            Assert.assertTrue(checkout.getCheckoutToken().length() > 5);


        } catch (CheckoutServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        }


    }


    @Test
    public void testAlterarEndereco(){
        try{
            CheckoutVo checkout = this.checkoutService.checkout(1l);
            checkout = checkoutService.alterarEndereco(checkout.getId(),2l);

            Assert.assertEquals(new Long(2l),checkout.getEndereco().getId());
            Assert.assertNotNull(checkout.getEndereco());
            Assert.assertNotNull(checkout.getEndereco().getEndereco());
            Assert.assertNotNull(checkout.getEndereco().getDescricao());

            Assert.assertEquals(new Double(28.50), checkout.getValorProdutos());
            Assert.assertTrue("Valor do frete = " + checkout.getValorFrete(), checkout.getValorFrete() > 0);
            Assert.assertTrue(checkout.getValorTotal() > checkout.getValorProdutos());
            Assert.assertEquals(new Double(0.0),checkout.getValorDesconto());
            Assert.assertNotNull(checkout.getCarrinho().getItems());
            Assert.assertEquals(1, checkout.getCarrinho().getItems().size());
            Assert.assertNotNull(checkout.getValorFrete());
            Assert.assertNotNull(checkout.getListResultFrete());
            Assert.assertEquals(2, checkout.getListResultFrete().size());
            Assert.assertEquals(FreteType.ECONOMICO, checkout.getFreteSelecionado().getFreteType());
            Assert.assertEquals(checkout.getValorFrete(), checkout.getFreteSelecionado().getValor());
            Assert.assertEquals(checkout.getDiasEntrega(), checkout.getFreteSelecionado().getDiasUteis());
            Assert.assertNotNull(checkout.getCheckoutToken());
            Assert.assertEquals(32,checkout.getCheckoutToken().length());


        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }

    @Test
    public void testAdicionarCupom(){
        try{
            CheckoutVo checkout = this.checkoutService.checkout(1l);
            Assert.assertNull(checkout.getCupom());

            checkout = checkoutService.adicionarCupom(checkout.getId(),"AABBCCDD");

            Assert.assertEquals(new Double(28.50), checkout.getValorProdutos());
            Assert.assertTrue(checkout.getValorFrete() > 0);
            Assert.assertTrue(checkout.getValorTotal() ==
                            (checkout.getValorProdutos() -
                            checkout.getValorDesconto() +
                            checkout.getValorFrete()));
            //validando se teve desconto de 50%
            Assert.assertEquals(new Double (checkout.getValorProdutos() * 50.0 /100.0), checkout.getValorDesconto());
            Assert.assertNotNull(checkout.getCarrinho());
            Assert.assertEquals(1, checkout.getCarrinho().getItems().size());
            Assert.assertNotNull(checkout.getEndereco());
            Assert.assertNotNull(checkout.getEndereco().getEndereco());
            Assert.assertNotNull(checkout.getEndereco().getDescricao());
            Assert.assertNotNull(checkout.getCupom());
            Assert.assertEquals(new Long(1l),checkout.getCupom().getId());
            Assert.assertNotNull(checkout.getValorFrete());
            Assert.assertNotNull(checkout.getListResultFrete());
            Assert.assertEquals(2, checkout.getListResultFrete().size());
            Assert.assertEquals(FreteType.ECONOMICO, checkout.getFreteSelecionado().getFreteType());
            Assert.assertEquals(checkout.getValorFrete(), checkout.getFreteSelecionado().getValor());
            Assert.assertEquals(checkout.getDiasEntrega(), checkout.getFreteSelecionado().getDiasUteis());
            Assert.assertNotNull(checkout.getCheckoutToken());
            Assert.assertEquals(32,checkout.getCheckoutToken().length());


        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }


    @Test
    public void testAdicionarCupomNaoExiste(){
        try{
            CheckoutVo checkout = this.checkoutService.checkout(1l);
            checkoutService.adicionarCupom(checkout.getId(),"XXXXX");

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage().contains("Cupom não existe"));
        }
    }

    @Test
    public void testAdicionarCupomExpirado(){
        try{
            CheckoutVo checkout = this.checkoutService.checkout(1l);
            checkoutService.adicionarCupom(checkout.getId(),"EEFFGGHH");

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage().contains("Cupom não existe"));
        }
    }


    @Test
    public void testAlterarFrete(){
        try{
            CheckoutVo checkout = this.checkoutService.checkout(1l);
            Assert.assertEquals(FreteType.ECONOMICO, checkout.getFreteSelecionado().getFreteType());

            checkout = checkoutService.alterarFrete(checkout.getId(), FreteType.RAPIDO);
            Assert.assertEquals(new Double(28.50), checkout.getValorProdutos());
            Assert.assertTrue(checkout.getValorFrete() > 0);
            Assert.assertTrue(checkout.getValorTotal() > checkout.getValorProdutos());
            Assert.assertNotNull(checkout.getCarrinho());
            Assert.assertEquals(1, checkout.getCarrinho().getItems().size());
            Assert.assertNotNull(checkout.getEndereco());
            Assert.assertNotNull(checkout.getEndereco().getEndereco());
            Assert.assertNotNull(checkout.getEndereco().getDescricao());
            Assert.assertNotNull(checkout.getValorFrete());
            Assert.assertNotNull(checkout.getListResultFrete());
            Assert.assertEquals(2, checkout.getListResultFrete().size());
            Assert.assertEquals(FreteType.RAPIDO, checkout.getFreteSelecionado().getFreteType());
            Assert.assertEquals(checkout.getValorFrete(), checkout.getFreteSelecionado().getValor());
            Assert.assertEquals(checkout.getDiasEntrega(), checkout.getFreteSelecionado().getDiasUteis());
            Assert.assertNotNull(checkout.getCheckoutToken());
            Assert.assertEquals(32,checkout.getCheckoutToken().length());

            CheckoutVo checkoutAtual = checkoutService.alterarFrete(checkout.getId(), FreteType.ECONOMICO);
            Assert.assertNotNull(checkoutAtual.getValorFrete());
            Assert.assertNotNull(checkoutAtual.getListResultFrete());
            Assert.assertEquals(2, checkoutAtual.getListResultFrete().size());
            Assert.assertEquals(FreteType.ECONOMICO, checkoutAtual.getFreteSelecionado().getFreteType());
            Assert.assertEquals(checkoutAtual.getValorFrete(), checkoutAtual.getFreteSelecionado().getValor());
            Assert.assertEquals(checkoutAtual.getDiasEntrega(), checkoutAtual.getFreteSelecionado().getDiasUteis());
            Assert.assertTrue("Valor Atual = " + checkoutAtual.getValorFrete() + " valor anterior = " + checkout.getValorFrete(),
                    checkoutAtual.getValorFrete() < checkout.getValorFrete());
            Assert.assertTrue("Dias atual = " + checkoutAtual.getDiasEntrega() + " dias anterior = " + checkout.getDiasEntrega(),
                    checkoutAtual.getDiasEntrega() > checkout.getDiasEntrega());


        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }

    @Test
    public void testGetCheckoutToken(){
        try{
            String token = this.checkoutService.getCheckoutToken();
            Assert.assertNotNull(token);
            Assert.assertEquals(32,token.length());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }
    }


    @Test
    public void testGetCheckout(){

        try {
            CheckoutVo checkout = this.checkoutService.checkout(1l);

            checkout = this.checkoutService.getCheckout(checkout.getId());

            Assert.assertEquals(new Double(28.50), checkout.getValorProdutos());
            Assert.assertTrue(checkout.getValorFrete() > 0);
            Assert.assertTrue(checkout.getValorTotal() > checkout.getValorProdutos());
            Assert.assertEquals(new Double(0.0),checkout.getValorDesconto());
            Assert.assertNotNull(checkout.getCarrinho().getItems());
            Assert.assertEquals(1, checkout.getCarrinho().getItems().size());
            Assert.assertNotNull(checkout.getEndereco());
            Assert.assertNotNull(checkout.getEndereco().getEndereco());
            Assert.assertNotNull(checkout.getEndereco().getDescricao());
            Assert.assertNotNull(checkout.getValorFrete());
            Assert.assertNotNull(checkout.getListResultFrete());
            Assert.assertEquals(2, checkout.getListResultFrete().size());
            Assert.assertEquals(FreteType.ECONOMICO, checkout.getFreteSelecionado().getFreteType());
            Assert.assertEquals(checkout.getValorFrete(), checkout.getFreteSelecionado().getValor());
            Assert.assertEquals(checkout.getDiasEntrega(), checkout.getFreteSelecionado().getDiasUteis());
            Assert.assertNotNull(checkout.getCheckoutToken());
            Assert.assertEquals(32,checkout.getCheckoutToken().length());


        } catch (CheckoutServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        }


    }

}

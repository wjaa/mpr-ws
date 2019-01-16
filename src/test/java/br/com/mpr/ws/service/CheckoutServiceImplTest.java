package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
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
            Assert.assertNotNull(checkout.getProdutos());
            Assert.assertEquals(1, checkout.getProdutos().size());
            Assert.assertNotNull(checkout.getEndereco());
            Assert.assertNotNull(checkout.getEndereco().getEndereco());
            Assert.assertNotNull(checkout.getEndereco().getDescricao());
            Assert.assertNotNull(checkout.getValorFrete());
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
            Assert.assertTrue(checkout.getValorFrete() > 0);
            Assert.assertTrue(checkout.getValorTotal() > checkout.getValorProdutos());
            Assert.assertEquals(new Double(0.0),checkout.getValorDesconto());
            Assert.assertNotNull(checkout.getProdutos());
            Assert.assertEquals(1, checkout.getProdutos().size());

            Assert.assertNotNull(checkout.getValorFrete());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }

    @Test
    public void testAdicionarCupom(){
        try{
            CheckoutVo checkout = this.checkoutService.checkout(1l);
            Assert.assertNull(checkout.getIdCupom());

            checkout = checkoutService.adicionarCupom(checkout.getId(),"AABBCCDD");

            Assert.assertEquals(new Double(28.50), checkout.getValorProdutos());
            Assert.assertTrue(checkout.getValorFrete() > 0);
            Assert.assertTrue(checkout.getValorTotal() ==
                            (checkout.getValorProdutos() -
                            checkout.getValorDesconto() +
                            checkout.getValorFrete()));
            //validando se teve desconto de 50%
            Assert.assertEquals(new Double (checkout.getValorProdutos() * 50.0 /100.0), checkout.getValorDesconto());
            Assert.assertNotNull(checkout.getProdutos());
            Assert.assertEquals(1, checkout.getProdutos().size());
            Assert.assertNotNull(checkout.getEndereco());
            Assert.assertNotNull(checkout.getEndereco().getEndereco());
            Assert.assertNotNull(checkout.getEndereco().getDescricao());
            Assert.assertEquals(new Long(1l),checkout.getIdCupom());
            Assert.assertNotNull(checkout.getValorFrete());

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


}

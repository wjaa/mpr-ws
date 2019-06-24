package br.com.mpr.ws.rest;

import br.com.mpr.ws.BaseMvcTest;
import br.com.mpr.ws.entity.FreteType;
import br.com.mpr.ws.utils.ObjectUtils;
import br.com.mpr.ws.vo.CheckoutVo;
import br.com.mpr.ws.vo.ErrorMessageVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;

public class CheckoutRestTest extends BaseMvcTest {

    @Test
    public void checkout() {

        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/core/checkout/1");

            String resultJson = ra.andReturn().getResponse().getContentAsString();
            CheckoutVo checkout = ObjectUtils.toObject(resultJson,CheckoutVo.class);
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

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }

    @Test
    public void adicionarCupom() {

        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/core/checkout/1");

            String resultJson = ra.andReturn().getResponse().getContentAsString();
            CheckoutVo checkout = ObjectUtils.toObject(resultJson,CheckoutVo.class);


            ra = getMvcPostResultAction("/api/v1/core/checkout/addCupom/" +
                    checkout.getId() + "/" + "AABBCCDD", "");

            resultJson = ra.andReturn().getResponse().getContentAsString();
            checkout = ObjectUtils.toObject(resultJson,CheckoutVo.class);

            Assert.assertEquals(new Double(28.50), checkout.getValorProdutos());
            Assert.assertTrue(checkout.getValorFrete() > 0);
            Assert.assertTrue(checkout.getValorTotal() ==
                    (checkout.getValorProdutos() -
                            checkout.getValorDesconto() +
                            checkout.getValorFrete()));
            //validando se teve desconto de 50%
            Assert.assertEquals(new Double (checkout.getValorProdutos() * 50.0 /100.0), checkout.getValorDesconto());
            Assert.assertNotNull(checkout.getCarrinho().getItems());
            Assert.assertEquals(1, checkout.getCarrinho().getItems().size());
            Assert.assertNotNull(checkout.getEndereco());
            Assert.assertNotNull(checkout.getEndereco().getEndereco());
            Assert.assertNotNull(checkout.getEndereco().getDescricao());
            Assert.assertEquals(new Long(1l),checkout.getCupom().getId());
            Assert.assertNotNull(checkout.getValorFrete());
            Assert.assertNotNull(checkout.getListResultFrete());
            Assert.assertEquals(2, checkout.getListResultFrete().size());
            Assert.assertEquals(FreteType.ECONOMICO, checkout.getFreteSelecionado().getFreteType());
            Assert.assertEquals(checkout.getValorFrete(), checkout.getFreteSelecionado().getValor());
            Assert.assertEquals(checkout.getDiasEntrega(), checkout.getFreteSelecionado().getDiasUteis());


        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }


    }

    @Test
    public void alterarEndereco() {

        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/core/checkout/1");

            String resultJson = ra.andReturn().getResponse().getContentAsString();
            CheckoutVo checkout = ObjectUtils.toObject(resultJson,CheckoutVo.class);

            ra = getMvcPostResultAction("/api/v1/core/checkout/alterarEndereco/" +
            checkout.getId() + "/2", "");

            resultJson = ra.andReturn().getResponse().getContentAsString();
            checkout = ObjectUtils.toObject(resultJson,CheckoutVo.class);

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



        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }



    }

    @Test
    public void alterarFrete() {

        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/core/checkout/1");

            String resultJson = ra.andReturn().getResponse().getContentAsString();
            CheckoutVo checkout = ObjectUtils.toObject(resultJson,CheckoutVo.class);

            ra = getMvcPostResultAction("/api/v1/core/checkout/alterarFrete/" +
            checkout.getId() + "/" + FreteType.RAPIDO.toString(),"");

            resultJson = ra.andReturn().getResponse().getContentAsString();
            checkout = ObjectUtils.toObject(resultJson,CheckoutVo.class);

            Assert.assertEquals(new Double(28.50), checkout.getValorProdutos());
            Assert.assertTrue(checkout.getValorFrete() > 0);
            Assert.assertTrue(checkout.getValorTotal() > checkout.getValorProdutos());
            Assert.assertNotNull(checkout.getCarrinho().getItems());
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

            ra = getMvcPostResultAction("/api/v1/core/checkout/alterarFrete/" +
                    checkout.getId() + "/" + FreteType.ECONOMICO.toString(),"");

            resultJson = ra.andReturn().getResponse().getContentAsString();
            CheckoutVo checkoutAtual = ObjectUtils.toObject(resultJson,CheckoutVo.class);

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
            Assert.assertTrue(ex.getMessage(),false);
        }
    }

    @Test
    public void testAdicionarCupomNaoExiste(){
        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/core/checkout/1");

            String resultJson = ra.andReturn().getResponse().getContentAsString();
            CheckoutVo checkout = ObjectUtils.toObject(resultJson,CheckoutVo.class);


            ra = getMvcPostErrorResultAction("/api/v1/core/checkout/addCupom/" +
                    checkout.getId() + "/" + "XXXX", "");

            resultJson = ra.andReturn().getResponse().getContentAsString();
            ErrorMessageVo errorMessageVo = ObjectUtils.toObject(resultJson,ErrorMessageVo.class);

            Assert.assertNotNull(errorMessageVo);
            Assert.assertEquals(errorMessageVo.getErrorMessage()[0],"Cupom não existe!");


        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }
    }

    @Test
    public void testAdicionarCupomExpirado(){
        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/core/checkout/1");

            String resultJson = ra.andReturn().getResponse().getContentAsString();
            CheckoutVo checkout = ObjectUtils.toObject(resultJson,CheckoutVo.class);


            ra = getMvcPostErrorResultAction("/api/v1/core/checkout/addCupom/" +
                    checkout.getId() + "/" + "EEFFGGHH", "");

            resultJson = ra.andReturn().getResponse().getContentAsString();
            ErrorMessageVo errorMessageVo = ObjectUtils.toObject(resultJson,ErrorMessageVo.class);

            Assert.assertNotNull(errorMessageVo);
            Assert.assertEquals(errorMessageVo.getErrorMessage()[0],"Cupom não existe!");

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage().contains("Cupom não existe"));
        }
    }

    @Test
    public void testGetCheckoutToken(){
        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/core/checkout/token");

            String token = ra.andReturn().getResponse().getContentAsString();
            Assert.assertNotNull(token);
            Assert.assertEquals(32,token.length());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }
    }

    protected AppUser getAppUser(){
        return getAppUserClient();
    }

}
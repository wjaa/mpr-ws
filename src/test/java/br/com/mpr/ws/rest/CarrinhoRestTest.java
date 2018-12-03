package br.com.mpr.ws.rest;

import br.com.mpr.ws.BaseMvcTest;
import br.com.mpr.ws.service.CarrinhoService;
import br.com.mpr.ws.utils.ObjectUtils;
import br.com.mpr.ws.utils.StringUtils;
import br.com.mpr.ws.vo.CarrinhoVo;
import br.com.mpr.ws.vo.ErrorMessageVo;
import br.com.mpr.ws.vo.ItemCarrinhoForm;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.ContentResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 */
public class CarrinhoRestTest extends BaseMvcTest {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private CarrinhoService carrinhoService;


    /**
     * USANDO UM CLIENTE NAO CADASTRADO
     * 1. Adicionar um produto no carrinho
     * 2. Resgatar o carrinho pelo keydevice e verificar se o produto está lá.
     *
     */
    @Test
    public void addItemCarrinhoSemCliente(){

        ItemCarrinhoForm item = new ItemCarrinhoForm();
        item.setKeyDevice("AAABBBCCCDDD");
        item.setIdProduto(5l);
        item.setFoto(new byte[]{10,10,10});
        item.setNomeArquivo(StringUtils.createRandomHash() + ".png");

        try{
            String content = ObjectUtils.toJson(item);
            ResultActions ra = getMvcPutResultAction("/api/v1/core/carrinho/add", content);

            String resultJson = ra.andReturn().getResponse().getContentAsString();

            ResultActions raGet = getMvcGetResultActions("/api/v1/core/carrinho/byKeyDevice/" + item.getKeyDevice());

            raGet.andExpect(content().json(resultJson));

            CarrinhoVo resultCarrinho = ObjectUtils.fromJSON(resultJson,CarrinhoVo.class);
            Assert.assertEquals("AAABBBCCCDDD",resultCarrinho.getKeyDevice());
            Assert.assertNotNull(resultCarrinho.getIdCarrinho());
            Assert.assertEquals(1, resultCarrinho.getItems().size());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }


    }

    /**
     * USANDO UM CLIENTE CADASTRADO
     * 1. Adicionar um produto no carrinho
     * 2. Resgatar o carrinho pelo id do cliente e verificar se o produto está lá.
     *
     */
    @Test
    public void addItemCarrinhoComCliente(){

        ItemCarrinhoForm item = new ItemCarrinhoForm();
        item.setIdCliente(1l);
        item.setIdProduto(5l);
        item.setFoto(new byte[]{10,10,10});
        item.setNomeArquivo(StringUtils.createRandomHash() + ".png");

        try{
            String content = ObjectUtils.toJson(item);
            ResultActions ra = getMvcPutResultAction("/api/v1/core/carrinho/add", content);

            String resultJson = ra.andReturn().getResponse().getContentAsString();

            ResultActions raGet = getMvcGetResultActions("/api/v1/core/carrinho/byIdCliente/" + item.getIdCliente());

            raGet.andExpect(content().json(resultJson));

            CarrinhoVo resultCarrinho = ObjectUtils.fromJSON(resultJson,CarrinhoVo.class);
            Assert.assertEquals(new Long(1),resultCarrinho.getIdCliente());
            Assert.assertNotNull(resultCarrinho.getIdCarrinho());
            Assert.assertEquals(1, resultCarrinho.getItems().size());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }



    /**
     *
     * TESTANDO A VALIDACAO DO FORM.
     * 1. Adicionar um produto no carrinho sem produto
     * 2. Adicionar um produto no carrinho sem foto e sem foto de catalogo.
     * 3. Adicionar um produto no carrinho com foto e sem nome
     * 4. Adicionar um produto no carrinho sem keydevice e sem idCliente
     *
     */
    @Test
    public void addItemCarrinhoValidation(){

        ItemCarrinhoForm item = new ItemCarrinhoForm();
        item.setIdCliente(1l);
        item.setFoto(new byte[]{10,10,10});
        item.setNomeArquivo(StringUtils.createRandomHash() + ".png");

        try{
            //#1
            String content = ObjectUtils.toJson(item);
            ResultActions ra = getMvcPutErrorResultAction("/api/v1/core/carrinho/add", content);
            Assert.assertTrue(ra.andReturn().getResponse().getContentAsString().contains("Produto é obrigatório"));

            //TODO CONTINUAR AQUI
            //#2

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }


    }




    /**
     *
     * 1. Buscar o carrinho cliente NAO cadastrado e SEM produto.
     *
     */
    @Test
    public void getCarrinhoSemClienteSemProduto(){
        String keyDevice = "AAABBBCCCDDD";
        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/core/carrinho/0/" + keyDevice);
            ra.andExpect(content().json(ObjectUtils.toJson(carrinhoService.getCarrinho(0l,keyDevice))));
            System.out.println(content());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }


    }

    /**
     *
     * 1. Buscar o carrinho cliente NAO cadastrado e COM produto.
     *
     */
    @Test
    public void getCarrinhoSemClienteComProduto(){
        String keyDevice = "AAABBBCCCDDD";
        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/core/carrinho/0/" + keyDevice);
            ra.andExpect(content().json(ObjectUtils.toJson(carrinhoService.getCarrinho(0l,keyDevice))));
            System.out.println(content());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }


    }

    /**
     * 1. Adicionar produto no carrinho.
     * 2. Verificar se produto esta no carrinho
     * 3. Remover produto do carrinho.
     * 4. Buscar carrinho e verificar se está vazio.
     */
    @Test
    public void removeCarrinho(){
        String keyDevice = "AAABBBCCCDDD";
        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/core/carrinho/0/" + keyDevice);
            ra.andExpect(content().json(ObjectUtils.toJson(carrinhoService.getCarrinho(0l,keyDevice))));
            System.out.println(content());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }

}

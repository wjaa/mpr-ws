package br.com.mpr.ws.rest;

import br.com.mpr.ws.BaseMvcTest;
import br.com.mpr.ws.service.CarrinhoService;
import br.com.mpr.ws.utils.ObjectUtils;
import br.com.mpr.ws.utils.StringUtils;
import br.com.mpr.ws.vo.AnexoVo;
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

import java.util.ArrayList;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 */
public class CarrinhoRestTest extends BaseMvcTest {


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
        item.setAnexos(new ArrayList<>());
        AnexoVo anexoVo = new AnexoVo();
        anexoVo.setFoto(new byte[]{0,0,0,0,0});
        anexoVo.setNomeArquivo(StringUtils.createRandomHash() + ".png");
        item.getAnexos().add(anexoVo);
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
        item.setAnexos(new ArrayList<>());
        AnexoVo anexoVo = new AnexoVo();
        anexoVo.setFoto(new byte[]{0,0,0,0,0});
        anexoVo.setNomeArquivo(StringUtils.createRandomHash() + ".png");
        item.getAnexos().add(anexoVo);

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
        item.setAnexos(new ArrayList<>());
        AnexoVo anexoVo = new AnexoVo();
        anexoVo.setFoto(new byte[]{0,0,0,0,0});
        anexoVo.setNomeArquivo(StringUtils.createRandomHash() + ".png");
        item.getAnexos().add(anexoVo);

        try{
            //#1
            String content = ObjectUtils.toJson(item);
            ResultActions ra = getMvcPutErrorResultAction("/api/v1/core/carrinho/add", content);
            Assert.assertTrue(ra.andReturn().getResponse().getContentAsString().contains("Produto é obrigatório"));

            //#2
            item.setIdProduto(5l);
            item.getAnexos().get(0).setFoto(null);
            content = ObjectUtils.toJson(item);
            ra = getMvcPutErrorResultAction("/api/v1/core/carrinho/add", content);
            Assert.assertTrue(ra.andReturn().getResponse().getContentAsString().contains("Para adicionar esse produto no carrinho, uma imagem é obrigatória"));

            //#3
            item.getAnexos().get(0).setFoto(new byte[]{10,10,10});
            item.getAnexos().get(0).setNomeArquivo("");
            content = ObjectUtils.toJson(item);
            ra = getMvcPutErrorResultAction("/api/v1/core/carrinho/add", content);
            Assert.assertTrue(ra.andReturn().getResponse().getContentAsString().contains("Nome da imagem está vazio"));

            //#4
            item.setIdCliente(null);
            content = ObjectUtils.toJson(item);
            ra = getMvcPutErrorResultAction("/api/v1/core/carrinho/add", content);
            Assert.assertTrue(ra.andReturn().getResponse().getContentAsString().contains("Id do cliente ou keyDevice é obrigatório."));

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
            ResultActions ra = getMvcGetResultActions("/api/v1/core/carrinho/byKeyDevice/" + keyDevice);
            CarrinhoVo carrinhoVo = ObjectUtils.fromJSON(ra.andReturn().getResponse().getContentAsString(),CarrinhoVo.class);
            Assert.assertNotNull(carrinhoVo);
            Assert.assertNull(carrinhoVo.getItems());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }


    }

    /**
     * 1. Adicionar produto no carrinho do cliente nao cadastrado
     * 2. Buscar o carrinho para cliente NAO cadastrado.
     *
     */
    @Test
    public void getCarrinhoSemClienteComProduto(){
        try{

            ItemCarrinhoForm item1 = createItemCarrinhoFormClienteDinamico(5l);
            String content = ObjectUtils.toJson(item1);
            getMvcPutResultAction("/api/v1/core/carrinho/add", content);

            ResultActions ra = getMvcGetResultActions("/api/v1/core/carrinho/byKeyDevice/" + item1.getKeyDevice());
            CarrinhoVo carrinhoVo = ObjectUtils.fromJSON(ra.andReturn().getResponse().getContentAsString(),CarrinhoVo.class);
            Assert.assertNotNull(carrinhoVo);
            Assert.assertEquals(1,carrinhoVo.getItems().size());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }


    }


    private ItemCarrinhoForm createItemCarrinhoFormClienteDinamico(Long idProduto) {
        ItemCarrinhoForm itemCarrinhoForm = new ItemCarrinhoForm();
        itemCarrinhoForm.setIdProduto(idProduto);
        itemCarrinhoForm.setKeyDevice(StringUtils.createRandomHash());
        itemCarrinhoForm.setAnexos(new ArrayList<>());
        AnexoVo anexoVo = new AnexoVo();
        anexoVo.setFoto(new byte[]{0,0,0,0,0});
        anexoVo.setNomeArquivo(StringUtils.createRandomHash() + ".png");
        itemCarrinhoForm.getAnexos().add(anexoVo);
        return itemCarrinhoForm;
    }

    /**
     * 1. Adicionar produto no carrinho.
     * 2. Verificar se produto esta no carrinho
     * 3. Remover produto do carrinho.
     * 4. Buscar carrinho e verificar se está vazio.
     */
    @Test
    public void removeCarrinho(){
        try{
            //#1
            ItemCarrinhoForm item1 = createItemCarrinhoFormClienteDinamico(5l);
            String content = ObjectUtils.toJson(item1);
            getMvcPutResultAction("/api/v1/core/carrinho/add", content);

            //#2
            ResultActions ra = getMvcGetResultActions("/api/v1/core/carrinho/byKeyDevice/" + item1.getKeyDevice());
            CarrinhoVo carrinhoVo = ObjectUtils.fromJSON(ra.andReturn().getResponse().getContentAsString(),CarrinhoVo.class);
            Assert.assertNotNull(carrinhoVo);
            Assert.assertEquals(item1.getKeyDevice(),carrinhoVo.getKeyDevice());
            Assert.assertTrue(carrinhoVo.getItems().size() == 1);

            //#3
            ra = getMvcDeleteResultActions("/api/v1/core/carrinho/removeItem/" + carrinhoVo.getItems().get(0).getId());
            carrinhoVo = ObjectUtils.fromJSON(ra.andReturn().getResponse().getContentAsString(),CarrinhoVo.class);
            Assert.assertNotNull(carrinhoVo);
            Assert.assertEquals(item1.getKeyDevice(),carrinhoVo.getKeyDevice());
            Assert.assertTrue(carrinhoVo.getItems().size() == 0);

            //#4
            ra = getMvcGetResultActions("/api/v1/core/carrinho/byKeyDevice/" + item1.getKeyDevice());
            carrinhoVo = ObjectUtils.fromJSON(ra.andReturn().getResponse().getContentAsString(),CarrinhoVo.class);
            Assert.assertNotNull(carrinhoVo);
            Assert.assertEquals(item1.getKeyDevice(),carrinhoVo.getKeyDevice());
            Assert.assertTrue(carrinhoVo.getItems().size() == 0);



        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }

    }

}

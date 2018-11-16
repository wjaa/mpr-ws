package br.com.mpr.ws.rest;

import br.com.mpr.ws.BaseMvcTest;
import br.com.mpr.ws.service.CarrinhoService;
import br.com.mpr.ws.utils.ObjectUtils;
import br.com.mpr.ws.vo.ItemCarrinhoForm;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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
     *
     */
    @Test
    public void addItemCarrinhoSemCliente(){

       /* ItemCarrinhoForm item = new ItemCarrinhoForm();
        item.setKeyDevice("AAABBBCCCDDD");
        item.setIdProduto(1l);
        item.setFoto(new Byte[]{10,10,10});

        try{
            ResultActions ra = mvc.perform(post("/api/v1/core/carrinho/add", item)
                    .with(httpBasic("user","password"))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content()
                            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

            ra.andExpect(content().json(ObjectUtils.toJson(carrinhoService.addCarrinho(item))));

            System.out.println(content());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }
*/

    }


    /**
     *
     */
    public void addItemCarrinhoComCliente(){

        ItemCarrinhoForm item = new ItemCarrinhoForm();
        item.setIdCliente(1l);
        item.setIdProduto(1l);
        item.setFoto(new Byte[]{10,10,10});

        try{
            ResultActions ra = mvc.perform(post("/api/v1/core/carrinho/add", item)
                    .with(httpBasic("user","password"))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content()
                            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

            ra.andExpect(content().json(ObjectUtils.toJson(carrinhoService.addCarrinho(item))));

            System.out.println(content());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }


    }



    /**
     *
     */
    public void getCarrinhoSemCliente(){
        String keyDevice = "AAABBBCCCDDD";
        try{
            ResultActions ra = mvc.perform(get("/api/v1/core/carrinho/0/" + keyDevice)
                    .with(httpBasic("user","password"))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content()
                            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

            ra.andExpect(content().json(ObjectUtils.toJson(carrinhoService.getCarrinho(0l,keyDevice))));

            System.out.println(content());

        }catch(Exception ex){
            Assert.assertTrue(ex.getMessage(),false);
        }


    }

    /**
     *
     */
    public void removeCarrinho(){

    }

}

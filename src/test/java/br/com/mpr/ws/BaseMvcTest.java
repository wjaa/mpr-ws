package br.com.mpr.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 */
@AutoConfigureMockMvc
public abstract class BaseMvcTest extends BaseDBTest {

    @Autowired
    private MockMvc mvc;

    public ResultActions getMvcGetResultActions(String endPoint) throws Exception {
        return mvc.perform(get(endPoint)
                .with(httpBasic("user","password"))
                .header("Origin","api.meuportaretrato.com")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    public ResultActions getMvcDeleteResultActions(String endPoint) throws Exception {
        return mvc.perform(delete(endPoint)
                .with(httpBasic("user","password"))
                .header("Origin","api.meuportaretrato.com")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    public ResultActions getMvcPutResultAction(String endPoint, String content) throws Exception {
        return mvc.perform(put(endPoint)
                .with(httpBasic("user","password"))
                .header("Origin","api.meuportaretrato.com")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    public ResultActions getMvcPutErrorResultAction(String endPoint, String content) throws Exception {
        return mvc.perform(put(endPoint)
                .with(httpBasic("user","password"))
                .header("Origin","api.meuportaretrato.com")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    public ResultActions getMvcPostResultAction(String endPoint, String content) throws Exception {
        return mvc.perform(post(endPoint)
                .with(httpBasic("user","password"))
                .header("Origin","api.meuportaretrato.com")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    public ResultActions getMvcPostErrorResultAction(String endPoint, String content) throws Exception {
        return mvc.perform(post(endPoint)
                .with(httpBasic("user","password"))
                .header("Origin","api.meuportaretrato.com")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

}

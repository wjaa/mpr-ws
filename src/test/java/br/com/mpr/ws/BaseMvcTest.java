package br.com.mpr.ws;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 */
@AutoConfigureMockMvc
public abstract class BaseMvcTest extends BaseDBTest {

    private static final Log LOG = LogFactory.getLog(BaseMvcTest.class);

    private static final String PASSWORD = "password";
    private static final String USER_CLIENT = "client";

    private boolean startTestAuthUser = false;

    @Autowired
    protected MockMvc mvc;

    protected class AppUser{

        private String password;
        private String username;

        public AppUser(String username, String password){
            this.username = username;
            this.password = password;
        }

        public String getPassword() {
            return password;
        }

        public String getUsername() {
            return username;
        }

    }


    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    String accessToken = "";

    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup((WebApplicationContext)this.ac)
                .addFilter(springSecurityFilterChain).build();
        try{
            AppUser user = getAppUser();
            accessToken = obtainAccessToken(user.getUsername(), user.getPassword());
        }catch(Exception ex){
            LOG.error("Erro ao obter o token de acesso", ex);
        }
    }


    private String obtainAccessToken(String username, String password) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "client_credentials");
        //params.add("client_id", "fooClientIdPassword");
        //params.add("username", username);
        //params.add("password", password);

        ResultActions result
                = mvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(username,password))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    public String obtainAccessTokenPassword() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        //params.add("client_id", "fooClientIdPassword");
        params.add("username", "wag184@gmail.com");
        params.add("password", "1234567");

        AppUser user = getAppUserClient();

        ResultActions result
                = mvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(user.getUsername(),user.getPassword()))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    public String obtainAccessTokenPassword(String cliId, String cliPass) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", cliId);
        params.add("password", cliPass);

        AppUser user = getAppUserClient();

        ResultActions result
                = mvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(user.getUsername(),user.getPassword()))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }


    public ResultActions getMvcGetResultActions(String endPoint) throws Exception {
        return mvc.perform(get(endPoint)
                .header("Authorization", "Bearer " + accessToken)
                .header("Origin","api.meuportaretrato.com")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    public ResultActions getMvcGetResultActions(String endPoint, String manualAccessToken) throws Exception {
        return mvc.perform(get(endPoint)
                .header("Authorization", "Bearer " + manualAccessToken)
                .header("Origin","api.meuportaretrato.com")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    public ResultActions getMvcDeleteResultActions(String endPoint) throws Exception {
        return mvc.perform(delete(endPoint)
                .header("Authorization", "Bearer " + accessToken)
                .header("Origin","api.meuportaretrato.com")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    public ResultActions getMvcDeleteResultActions(String endPoint, String manualAccessToken) throws Exception {
        return mvc.perform(delete(endPoint)
                .header("Authorization", "Bearer " + manualAccessToken)
                .header("Origin","api.meuportaretrato.com")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    public ResultActions getMvcPutResultAction(String endPoint, String content) throws Exception {
        return mvc.perform(put(endPoint)
                .header("Authorization", "Bearer " + accessToken)
                .header("Origin","api.meuportaretrato.com")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    public ResultActions getMvcPutResultAction(String endPoint, String manualAccessToken, String content) throws Exception {
        return mvc.perform(put(endPoint)
                .header("Authorization", "Bearer " + manualAccessToken)
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
                .header("Authorization", "Bearer " + accessToken)
                .header("Origin","api.meuportaretrato.com")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    public ResultActions getMvcPutErrorResultAction(String endPoint, String manualAccessToken, String content) throws Exception {
        return mvc.perform(put(endPoint)
                .header("Authorization", "Bearer " + manualAccessToken)
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
                .header("Authorization", "Bearer " + accessToken)
                .header("Origin","api.meuportaretrato.com")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    public ResultActions getMvcPostResultAction(String endPoint,String manualAccessToken, String content) throws Exception {
        return mvc.perform(post(endPoint)
                .header("Authorization", "Bearer " + manualAccessToken)
                .header("Origin","api.meuportaretrato.com")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    public ResultActions getMvcPostFormResultAction(String endPoint, String content) throws Exception {
        return mvc.perform(post(endPoint)
                .header("Authorization", "Bearer " + accessToken)
                .header("Origin","api.meuportaretrato.com")
                .content(content)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    public ResultActions getMvcPostErrorResultAction(String endPoint, String content) throws Exception {
        return mvc.perform(post(endPoint)
                .header("Authorization", "Bearer " + accessToken)
                .header("Origin","api.meuportaretrato.com")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    public ResultActions getMvcPostFormErrorResultAction(String endPoint, String content) throws Exception {
        return mvc.perform(post(endPoint)
                .header("Authorization", "Bearer " + accessToken)
                .header("Origin","api.meuportaretrato.com")
                .content(content)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    protected abstract AppUser getAppUser();

    public final AppUser getAppUserClient(){
        return new AppUser("client","password");
    }

    public final AppUser getAppUserAdmin(){
        return new AppUser("admin","password");
    }


    public void setStartTestAuthUser(boolean start){
        this.startTestAuthUser = start;
    }

}

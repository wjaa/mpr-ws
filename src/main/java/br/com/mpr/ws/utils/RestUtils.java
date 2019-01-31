package br.com.mpr.ws.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import br.com.mpr.ws.exception.RestException;
import br.com.mpr.ws.vo.ErrorMessageVo;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Created by wagner on 16/06/15.
 */
@Component
public class RestUtils {

    private static final Log LOG = LogFactory.getLog(RestUtils.class);
    private static final CloseableHttpClient httpclient = HttpClients.createDefault();
    private static CloseableHttpClient apiHttpclient = null;
    private static final int TIMEOUT_MILLIS_40s = 1000*40; //40s
    private static final int TIMEOUT_MILLIS_180s = 1000*180; //3m


    public static <T>T getJsonWithParamPath(Class<T> clazzReturn, String targetUrl, String ... params) throws
            RestException {


        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet("http://" + targetUrl + "/" + RestUtils.createParamsPath(params));
            response = httpclient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            if ( statusCode >= 400 && statusCode < 500){
                LOG.error("Error code:" + statusCode + " response: " + response);
                throw new RestException("Servico está fora do ar.");
            }

            if (statusCode >= 500 && statusCode < 600){
                throw new RestException(ObjectUtils.fromJSON(EntityUtils.toString(response.getEntity()), ErrorMessageVo.class));
            }

            LOG.debug("m=getJsonWithParamPath Response: " + response.getStatusLine());

            return ObjectUtils.fromJSON(EntityUtils.toString(response.getEntity()), clazzReturn);

        } catch (Exception e) {
            throw new RestException(e.getMessage());
        } finally {
            try{
                if (response != null){
                    response.close();
                }
            }catch(Exception ex){
                LOG.error("Erro ao fechar a conexao.", ex);
            }

        }
    }

    public static <T>T postJson(Class<T> clazzReturn, String targetUrl, String uri, String json) throws
            RestException {


        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost("http://" + targetUrl + "/" + uri);

            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(TIMEOUT_MILLIS_40s)
                    .setConnectTimeout(TIMEOUT_MILLIS_40s)
                    .setConnectionRequestTimeout(TIMEOUT_MILLIS_40s)
                    .build();

            httpPost.setConfig(requestConfig);


            httpPost.setEntity(new StringEntity(json, ContentType.create("application/json", Consts.UTF_8)));
            response = httpclient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();

            if ( statusCode >= 400 && statusCode < 500){
                LOG.error("Error code:" + statusCode + " response: " + response);
                throw new RestException("Serviço está fora do ar.");
            }

            if (statusCode >= 500 && statusCode < 600){
                throw new RestException(ObjectUtils.fromJSON(EntityUtils.toString(response.getEntity()), ErrorMessageVo.class));
            }

            LOG.debug("m=getJsonWithParamPath Response: " + response.getStatusLine());

            return ObjectUtils.fromJSON(EntityUtils.toString(response.getEntity()), clazzReturn);
        }  catch (Exception e) {
            LOG.error("Erro:", e);
            throw new RestException(new ErrorMessageVo(500,e.getMessage()));
        } finally {
            try{
                if (response != null){
                    response.close();
                }
            }catch(Exception ex){
                LOG.error("Erro ao fechar a conexao.", ex);
            }

        }
    }



    public static byte[] getDownload(String targetUrl, String uri) throws RestException {


        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet("http://" + targetUrl + "/" + uri);

            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(TIMEOUT_MILLIS_180s)
                    .setConnectTimeout(TIMEOUT_MILLIS_180s)
                    .setConnectionRequestTimeout(TIMEOUT_MILLIS_180s)
                    .build();

            httpGet.setConfig(requestConfig);


            response = httpclient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            if ( statusCode >= 400 && statusCode < 500){
                LOG.error("Error code:" + statusCode + " response: " + response);
                throw new RestException("Serviço está fora do ar.");
            }

            if (statusCode >= 500 && statusCode < 600){
                throw new RestException(ObjectUtils.fromJSON(EntityUtils.toString(response.getEntity()), ErrorMessageVo.class));
            }

            LOG.debug("m=getJsonWithParamPath Response: " + response.getStatusLine());

            HttpEntity e = response.getEntity();

            if (e != null){
                return IOUtils.toByteArray(e.getContent());
            }

            throw new RestException("Fatura nao encontrada");


        }  catch (Exception e) {
            throw new RestException(new ErrorMessageVo(500,e.getMessage()));
        } finally {
            try{
                if (response != null){
                    response.close();
                }
            }catch(Exception ex){
                LOG.error("Erro ao fechar a conexao.", ex);
            }

        }
    }

    private static String createParamsPath(String[] params) {
        String result = "";
        for(String p : params){
            result += "/" + p;
        }
        return result;

    }



    public static <T>T post(Class<T> clazzReturn, String targetUrl,
                            String uri, Map<String, String> params) throws RestException{

        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost("http://" + targetUrl + "/" + uri);

            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            for (String key : params.keySet()){
                postParameters.add(new BasicNameValuePair(key,params.get(key)));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(postParameters));
            response = httpclient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();

            if ( statusCode >= 400 && statusCode < 500){
                LOG.error("Error code:" + statusCode + " response: " + response);
                throw new RestException("Servico está fora do ar.");
            }

            if (statusCode >= 500 && statusCode < 600){
                throw new RestException(ObjectUtils.fromJSON(EntityUtils.toString(response.getEntity()), ErrorMessageVo.class));
            }

            LOG.debug("m=getJsonWithParamPath Response: " + response.getStatusLine());

            return ObjectUtils.fromJSON(EntityUtils.toString(response.getEntity()), clazzReturn);

        }  catch (Exception e) {
            throw new RestException(new ErrorMessageVo(500,e.getMessage()));
        } finally {
            try{
                if (response != null){
                    response.close();
                }
            }catch(Exception ex){
                LOG.error("Erro ao fechar a conexao.", ex);
            }

        }
    }


    public static String post(String url, Map<String, String> params) throws RestException{

        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> postParameters = new ArrayList<>();
            for (String key : params.keySet()){
                postParameters.add(new BasicNameValuePair(key,params.get(key)));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(postParameters));
            response = httpclient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();

            if ( statusCode >= 400 && statusCode < 500){
                LOG.error("Error code:" + statusCode + " response: " + response);
                throw new RestException("Servico está fora do ar.");
            }

            if (statusCode >= 500 && statusCode < 600){
                throw new RestException(ObjectUtils.fromJSON(EntityUtils.toString(response.getEntity()), ErrorMessageVo.class));
            }

            LOG.debug("m=post Response: " + response.getStatusLine());

            return EntityUtils.toString(response.getEntity());

        }  catch (Exception e) {
            LOG.error("Erro ao realizar o post", e);
            throw new RestException(new ErrorMessageVo(500,e.getMessage()));
        } finally {
            try{
                if (response != null){
                    response.close();
                }
            }catch(Exception ex){
                LOG.error("Erro ao fechar a conexao.", ex);
            }

        }
    }

    public static <T>T apiGet(Class<T> clazz, String endPoint) throws RestException {
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(endPoint);
            response = apiHttpclient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            if ( statusCode == 404 ){
                LOG.error("Error code:" + statusCode + " response: " + response);
                throw new RestException("Erro interno ou serviço está fora do ar.");
            }

            if (statusCode >= 400 && statusCode < 600){
                try {
                    ErrorMessageVo errorMessage = ObjectUtils.fromJSON(EntityUtils.toString(response.getEntity()), ErrorMessageVo.class);
                    throw new RestException(errorMessage);
                }catch(RestException e) {
                    throw e;
                }catch(Exception e) {
                    throw new RestException("Erro interno ou Serviço está fora do ar.");
                }
            }

            LOG.debug("m=apiGet Response: " + response.getStatusLine());

            return ObjectUtils.fromJSON(EntityUtils.toString(response.getEntity()), clazz);

        } catch (Exception e) {
            throw new RestException(e.getMessage());
        } finally {
            try{
                if (response != null){
                    response.close();
                }
            }catch(Exception ex){
                LOG.error("Erro ao fechar a conexao.", ex);
            }

        }
    }



    public static <T>T apiPost(Class<T> clazz, String endPoint) throws RestException {
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(endPoint);
            response = apiHttpclient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();

            if ( statusCode == 404 ){
                LOG.error("Error code:" + statusCode + " response: " + response);
                throw new RestException("Erro interno ou serviço está fora do ar.");
            }

            if (statusCode >= 400 && statusCode < 600){
                try {
                    ErrorMessageVo errorMessage = ObjectUtils.fromJSON(EntityUtils.toString(response.getEntity()), ErrorMessageVo.class);
                    throw new RestException(errorMessage);
                }catch(RestException e) {
                    throw e;
                }catch(Exception e) {
                    throw new RestException("Erro interno ou Serviço está fora do ar.");
                }
            }

            LOG.debug("m=apiPost Response: " + response.getStatusLine());

            return ObjectUtils.fromJSON(EntityUtils.toString(response.getEntity()), clazz);

        } catch (Exception e) {
            throw new RestException(e.getMessage());
        } finally {
            try{
                if (response != null){
                    response.close();
                }
            }catch(Exception ex){
                LOG.error("Erro ao fechar a conexao.", ex);
            }

        }
    }

    public static <T>T apiDetele(Class<T> clazz, String endPoint) throws RestException {
        CloseableHttpResponse response = null;
        try {
            HttpDelete httpDelete = new HttpDelete(endPoint);
            response = apiHttpclient.execute(httpDelete);
            int statusCode = response.getStatusLine().getStatusCode();

            if ( statusCode == 404 ){
                LOG.error("Error code:" + statusCode + " response: " + response);
                throw new RestException("Erro interno ou serviço está fora do ar.");
            }

            if (statusCode >= 400 && statusCode < 600){
                try {
                    ErrorMessageVo errorMessage = ObjectUtils.fromJSON(EntityUtils.toString(response.getEntity()), ErrorMessageVo.class);
                    throw new RestException(errorMessage);
                }catch(RestException e) {
                    throw e;
                }catch(Exception e) {
                    throw new RestException("Erro interno ou Serviço está fora do ar.");
                }
            }

            LOG.debug("m=apiPost Response: " + response.getStatusLine());

            return ObjectUtils.fromJSON(EntityUtils.toString(response.getEntity()), clazz);

        } catch (Exception e) {
            throw new RestException(e.getMessage());
        } finally {
            try{
                if (response != null){
                    response.close();
                }
            }catch(Exception ex){
                LOG.error("Erro ao fechar a conexao.", ex);
            }

        }
    }

}
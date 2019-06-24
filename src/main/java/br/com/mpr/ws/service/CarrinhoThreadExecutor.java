package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.ProdutoPreviewEntity;
import br.com.mpr.ws.exception.CarrinhoServiceException;
import br.com.mpr.ws.vo.CarrinhoVo;
import br.com.mpr.ws.vo.ItemCarrinhoForm;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Essa classe encapsula os parametros e resultados de execução das Thread de request para adicionar produtos
 * no carrinho dos clientes.
 *
 * Created by wagner on 11/25/18.
 */
public class CarrinhoThreadExecutor {

    private static final Map<String,ProdutoPreviewEntity> mapExecuteCarrinho = new ConcurrentHashMap<>();
    private static final Map<String,CarrinhoVo> mapResultCarrinho = new ConcurrentHashMap<>();
    private static final Map<String,CarrinhoServiceException> mapException = new ConcurrentHashMap<>();
    public static final Long TIMEOUT = 1000*30l; //30s
    private static boolean threadAlive = false;

    public static void putExecute(String key, ProdutoPreviewEntity item){
        mapExecuteCarrinho.put(key,item);
    }

    public static void putResult(String key, CarrinhoVo carrinho){
        mapResultCarrinho.put(key,carrinho);
    }

    public static void putException(String key, CarrinhoServiceException e){
        mapException.put(key,e);
    }

    public static boolean containsExecute(String key){
        return mapExecuteCarrinho.containsKey(key);
    }

    public static boolean containsResult(String key){
        return mapResultCarrinho.containsKey(key);
    }

    public static boolean containsException(String key){
        return mapException.containsKey(key);
    }

    public static void removeExecute(String key) {
        mapExecuteCarrinho.remove(key);
    }

    public static void removeResult(String key) {
        mapResultCarrinho.remove(key);
    }

    public static void removeException(String key) {
        mapException.remove(key);
    }

    public static CarrinhoVo getResult(String key) {
        return mapResultCarrinho.get(key);
    }

    public static CarrinhoServiceException getException(String key) {
        return mapException.get(key);
    }

    public static boolean isAlive() {
        return threadAlive;
    }

    public static void setAlive(boolean alive){
        CarrinhoThreadExecutor.threadAlive = alive;
    }

    public static ProdutoPreviewEntity getExecute(String key) {
        return mapExecuteCarrinho.get(key);
    }

    public static Iterator<String> getIterator() {
        return mapExecuteCarrinho.keySet().iterator();
    }

    public static boolean isEmpty(){
        return mapExecuteCarrinho.isEmpty();
    }
}

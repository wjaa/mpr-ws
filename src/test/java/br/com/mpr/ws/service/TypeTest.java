package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.FreteType;
import br.com.mpr.ws.entity.PagamentoType;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by wagner on 17/02/19.
 */
public class TypeTest {


    /**
     * Se esse metodo der erro é porque foi trocada a ordem do ENUM.
     * Esse enum precisa ter a ordem exata do teste.
     */
    @Test
    public void testFreteType(){
        Assert.assertEquals(0,FreteType.ECONOMICO.ordinal());
        Assert.assertEquals(1,FreteType.RAPIDO.ordinal());
    }

    /**
     * Se esse metodo der erro é porque foi trocada a ordem do ENUM.
     * Esse enum precisa ter a ordem exata do teste.
     */
    @Test
    public void testPagamentoType(){
        Assert.assertEquals(0, PagamentoType.BOLETO.ordinal());
        Assert.assertEquals(1,PagamentoType.CARTAO_CREDITO.ordinal());
    }

}

package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.entity.MprParameterType;
import br.com.mpr.ws.utils.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;

/**
 * Created by wagner on 25/02/19.
 */
public class MprParameterServiceImplTest extends BaseDBTest {

    @Autowired
    private MprParameterService parameterService;


    @Test
    public void getParameter() throws Exception {
        Assert.assertEquals("07093090",
                parameterService.getParameter(MprParameterType.CEP_ORIGEM,"default"));
    }

    @Test
    public void getParameterBoolean() throws Exception {
        //quanto tiver um parametro booleano, implementar teste.
    }

    @Test
    public void getParameterDate() throws Exception {
        //quanto tiver um parametro data, implementar teste.
    }

    @Test
    public void getParameterDouble() throws Exception {
        //quanto tiver um parametro double, implementar teste.
    }

    @Test
    public void getParameterLong() throws Exception {
        //quanto tiver um parametro long, implementar teste.
    }

    @Test
    public void getParameterInteger() throws Exception {
        Assert.assertEquals(new Integer(4),
                parameterService.getParameterInteger(MprParameterType.MARGEM_PROTECAO,0));
    }

}
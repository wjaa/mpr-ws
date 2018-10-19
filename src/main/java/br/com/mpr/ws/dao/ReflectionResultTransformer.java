package br.com.mpr.ws.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.transform.ResultTransformer;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wagner on 6/24/18.
 */
public class ReflectionResultTransformer implements ResultTransformer {

    private static final Log LOG = LogFactory.getLog(ReflectionResultTransformer.class);

    private Class clazzVo;


    ReflectionResultTransformer(Class clazzVo){
        this.clazzVo = clazzVo;
    }


    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {

        try {
            Object o  = clazzVo.newInstance();
            for (int i = 0; i < aliases.length; i ++){
                Method method = getMethod(aliases[i]);

                if (method == null){
                    LOG.error("Nao encontramos um field para a coluna " + aliases[i] + " na classe " + clazzVo.getName());
                }else{

                    try{
                        Object value = getValue(tuple[i],method);
                        ReflectionUtils.invokeMethod(method,o,value);
                    }catch(Exception ex){
                        LOG.error("Erro ao invocar o metodo: " + method.getName() + " com parametro: " + tuple[i] +
                                " e tipo = " + tuple[i].getClass(), ex);
                        throw ex;
                    }


                }
            }
            return o;
        } catch (InstantiationException | IllegalAccessException  e) {
            LOG.error("Erro na tentativa de inserir um valor no POJO:", e);
        }


        return null;
    }

    private Object getValue(Object o, Method method) {
        Class<?> typeParam = method.getParameterTypes()[0];
        if (o instanceof Number && typeParam == Long.class){
            return ((Number)o).longValue();
        }
        if (o instanceof Number && typeParam == Integer.class){
            return ((Number)o).intValue();
        }
        if (o instanceof Number && typeParam == Double.class){
            return ((Number)o).doubleValue();
        }
        if (o instanceof Number && typeParam == Boolean.class){
            return new Boolean(((Number)o).intValue() == 1) ;
        }
        if (o instanceof BigDecimal && typeParam == Double.class){
            return ((BigDecimal)o).doubleValue();
        }
        return o;
    }

    private Method getMethod(String aliase) {
        String methodName = "set" + aliase.replace("_","").replace(" ","").trim().toLowerCase();
        Method method = Arrays.asList(clazzVo.getDeclaredMethods())
                .stream()
                .filter(m -> m.getName().equalsIgnoreCase(methodName))
                .findFirst().orElse(null);
        return method;
    }


    @Override
    public List transformList(List collection) {
        return collection;
    }
}

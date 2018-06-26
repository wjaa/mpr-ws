package br.com.mpr.ws.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.transform.ResultTransformer;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
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
                Field field = getField(aliases[i]);

                if (field == null){
                    LOG.error("Nao encontramos um field para a coluna " + aliases[i] + " na classe " + clazzVo.getName());
                }else{
                    ReflectionUtils.setField(field, o, tuple[i]);
                }
            }
            return o;
        } catch (InstantiationException | IllegalAccessException  e) {
            LOG.error("Erro na tentativa de inserir um valor no POJO:", e);
        }

        return null;
    }

    private Field getField(String aliase) {
        String fieldName = aliase.replace("_","").replace(" ","").trim().toLowerCase();
        Field findField = Arrays.asList(clazzVo.getDeclaredFields())
                .stream()
                .filter(field -> field.getName().equalsIgnoreCase(fieldName))
                .findFirst().orElse(null);
        return findField;
    }

    @Override
    public List transformList(List collection) {
        return collection;
    }
}

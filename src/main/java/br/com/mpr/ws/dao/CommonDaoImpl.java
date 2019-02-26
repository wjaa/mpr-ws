package br.com.mpr.ws.dao;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by wagner on 6/24/18.
 */
@Repository
public class CommonDaoImpl implements CommonDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> T get(Class<T> clazz, Serializable id) {
        T o = entityManager.find(clazz,id);
        return o;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> List<T> listAll(Class<T> clazz) {
        return entityManager
                .createQuery("select e from " + clazz.getName() + " e")
                .getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public <T> T save(T o) {
        entityManager.persist(o);
        return o;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public <T> T update(T o) {
        o = entityManager.merge(o);
        return o;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> List<T> findByNativeQuery(String query, Class<T> resultClass) {
        return findByNativeQuery(query,resultClass,false);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> List<T> findByNativeQuery(String query, Class<T> resultClass, boolean ignoreEntity) {
        if (resultClass.isAnnotationPresent(Entity.class) && !ignoreEntity){
            Query q = entityManager.createNativeQuery(query, resultClass);
            return q.getResultList();
        }else{
            Session session = (Session) entityManager.getDelegate();
            Session s = session.getSessionFactory().openSession();
            NativeQuery nq = s.createNativeQuery(query);
            nq.setResultTransformer(new ReflectionResultTransformer(resultClass));
            List<T> result = nq.getResultList();
            s.close();
            return result;

        }

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> List<T> findByProperties(Class<T> clazzEntity, String[] params, Object[] values) {
        List<T> result = getResultQuery(clazzEntity, params, values);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> List<T> findByInProperties(Class<T> clazzEntity, String param, List values) {
        if ( !clazzEntity.isAnnotationPresent(Entity.class) ){
            throw new IllegalArgumentException("clazzEntity nao eh uma entidade.");
        }
        Assert.notNull(param, "params nao pode estar nulo");
        Assert.notEmpty(values, "values nao pode estar nulo");

        final StringBuilder sql = new StringBuilder();
        sql.append("Select e from " + clazzEntity.getSimpleName() + " e ");
        sql.append(" where 1 = 1 ");
        sql.append(" and e." + param + " in :" + param );

        Session session = (Session) entityManager.getDelegate();
        Session s = session.getSessionFactory().openSession();
        Query query = s.createQuery(sql.toString());
        ((org.hibernate.query.Query) query).setParameterList(param,values);
        List<T> result = query.getResultList();
        s.close();
       return result;
    }

    private List getResultQuery(Class clazzEntity, String[] params, Object[] values) {
        if ( !clazzEntity.isAnnotationPresent(Entity.class) ){
            throw new IllegalArgumentException("clazzEntity nao eh uma entidade.");
        }
        Assert.noNullElements(params, "params nao pode estar nulo");
        Assert.noNullElements(values, "value nao pode estar nulo");
        Assert.state(params.length == values.length, "Falta parametro ou valor, params.length["
                + params.length + "] values.length[" + values.length + "]");


        final StringBuilder sql = new StringBuilder();
        sql.append("Select e from " + clazzEntity.getSimpleName() + " e ");
        sql.append(" where 1 = 1 ");
        Arrays.asList(params).forEach(p -> sql.append(" and e." + p + " = :" + p.replace(".","") ));

        Session session = (Session) entityManager.getDelegate();
        Session s = session.getSessionFactory().openSession();
        Query query = s.createQuery(sql.toString());

        for (int i = 0 ; i < values.length ; i++){
            query.setParameter(params[i].replace(".",""),values[i]);
        }
        List result = query.getResultList();
        s.close();
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> T findByPropertiesSingleResult(Class<T> clazzEntity, String[] params, Object[] values) {
        List<T> listResult = getResultQuery(clazzEntity, params, values);
        if (listResult != null && listResult.size() > 0){
            return listResult.get(0);
        }

        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public <T>void remove(Class<T> clazz, Serializable id) {
        entityManager.remove(get(clazz,id));
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> List<T> findByNativeQuery(String query, Class<T> resultClass, String [] nameParams, Object [] params) {
        return this.findByNativeQuery(query,resultClass,nameParams,params,false);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> List<T> findByNativeQuery(String query, Class<T> resultClass, String [] nameParams, Object [] params, boolean ignoreEntity) {
        if (resultClass.isAnnotationPresent(Entity.class) &&  !ignoreEntity) {

            Query q = entityManager.createNativeQuery(query, resultClass);
            for (int i = 0; i < nameParams.length; i++) {
                q.setParameter(nameParams[i], params[i]);
            }
            return q.getResultList();
        }else{
            Session session = (Session) entityManager.getDelegate();
            Session s = session.getSessionFactory().openSession();
            NativeQuery nq = s.createNativeQuery(query);
            nq.setResultTransformer(new ReflectionResultTransformer(resultClass));
            for (int i = 0; i < nameParams.length; i++) {
                nq.setParameter(nameParams[i], params[i]);
            }
            List<T> result = nq.getResultList();
            s.close();
            return result;
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> T findByNativeQuerySingleResult(String query, Class<T> resultClass, String [] nameParams, Object [] params) {
        List<T> list = findByNativeQuery(query,resultClass,nameParams,params);
        return list.size() > 0 ? list.get(0) : null;
    }



    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> List<T> findByNativeQuery(String query, Class<T> resultClass, Object... params) {
        if (resultClass.isAnnotationPresent(Entity.class)) {

            Query q = entityManager.createNativeQuery(query, resultClass);
            int i = 1;
            for (Object o : params) {
                q.setParameter(i++, o);
            }
            return q.getResultList();
        }else{
            Session session = (Session) entityManager.getDelegate();
            Session s = session.getSessionFactory().openSession();
            NativeQuery nq = s.createNativeQuery(query);
            nq.setResultTransformer(new ReflectionResultTransformer(resultClass));
            int i = 1;
            for (Object o : params) {
                nq.setParameter(i++, o);
            }
            List<T> result = nq.getResultList();
            s.close();
            return result;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int executeUpdate(String query, Object... params) {
        Query q = entityManager.createNativeQuery(query);
        IntStream.range(0,params.length).forEach(i -> q.setParameter(i+1, params[i]));
        return q.executeUpdate();
    }

}

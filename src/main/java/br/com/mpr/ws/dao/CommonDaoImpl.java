package br.com.mpr.ws.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate5.HibernateTemplate;
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
    public <T> T get(Class<T> clazz, Serializable id) {
        return entityManager.find(clazz,id);
    }

    @Override
    public <T> List<T> listAll(Class<T> clazz) {
        return entityManager
                .createQuery("select e from " + clazz.getSimpleName() + " e")
                .getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T save(T o) {
        entityManager.persist(o);
        return o;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T update(T o) {
        return entityManager.merge(o);
    }

    @Override
    public <T> List<T> findByNativeQuery(String query, Class<T> resultClass) {
        if (resultClass.isAnnotationPresent(Entity.class)){
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
    public <T> List<T> findByProperties(Class<T> clazzEntity, String[] params, Object[] values) {
        Query query = createQuery(clazzEntity, params, values);
        return query.getResultList();
    }

    private Query createQuery(Class clazzEntity, String[] params, Object[] values) {
        if ( !clazzEntity.isAnnotationPresent(Entity.class) ){
            throw new IllegalArgumentException("clazzEntity nao eh uma entidade.");
        }
        Assert.noNullElements(params, "params nao pode estar nulo");
        Assert.noNullElements(values, "value nao pode estar nulo");
        Assert.state(params.length == values.length, "Falta parametro ou valor, params.length["
                + params.length + "] values.length[" + values.length + "]");


        final StringBuilder sql = new StringBuilder();
        sql.append("Select e from " + clazzEntity.getName() + " e ");
        sql.append(" where 1 = 1 ");
        Arrays.asList(params).forEach(p -> sql.append(" and e." + p + " = :" + p ));

        Session session = (Session) entityManager.getDelegate();
        Session s = session.getSessionFactory().openSession();
        Query query = s.createQuery(sql.toString());

        for (int i = 0 ; i < values.length ; i++){
            query.setParameter(params[i],values[i]);
        }

        return query;
    }

    @Override
    public <T> T findByPropertiesSingleResult(Class<T> clazzEntity, String[] params, Object[] values) {
        Query query = createQuery(clazzEntity, params, values);

        List<T> listResult = query.getResultList();

        if (listResult != null && listResult.size() > 0){
            return listResult.get(0);
        }

        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T>void remove(Class<T> clazz, Serializable id) {
        entityManager.remove(get(clazz,id));
    }

    @Override
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int executeUpdate(String query, Object... params) {
        Query q = entityManager.createNativeQuery(query);
        IntStream.range(0,params.length).forEach(i -> q.setParameter(i+1, params[i]));
        return q.executeUpdate();
    }

}
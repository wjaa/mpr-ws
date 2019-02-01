package br.com.mpr.ws.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wagner on 6/24/18.
 */
public interface CommonDao{

    <T>T get(Class<T> clazz, Serializable id);

    <T>List<T> listAll(Class<T> clazz);

    <T> T save(T o);

    <T> T update(T o);

    <T> List<T> findByNativeQuery(String query, Class<T> resultClass);

    <T> List<T> findByNativeQuery(String query, Class<T> resultClass, Object ... params );

    int executeUpdate(String query, Object ... params );

    <T> List<T> findByNativeQuery(String query, Class<T> resultClass, String [] nameParams, Object [] params,
                                  boolean ignoreEntity);

    <T> List<T> findByProperties(Class<T> clazzEntity, String[] params, Object[] values);

    <T> List<T> findByInProperties(Class<T> clazzEntity, String params, List values);

    <T> T findByPropertiesSingleResult(Class<T> clazzEntity, String[] params, Object[] values);

    <T>void remove(Class<T> clazz, Serializable id);

    <T> List<T> findByNativeQuery(String query, Class<T> resultClass, String [] nameParams, Object [] params) ;

    <T> T findByNativeQuerySingleResult(String query, Class<T> resultClass, String [] nameParams, Object [] params);

    <T>List<T> findByNativeQuery(String query, Class<T> resultClass, boolean ignoreEntity);
}

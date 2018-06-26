package br.com.mpr.ws.dao;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.entity.ClienteEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by wagner on 6/24/18.
 */
public class CommonDaoImplTest extends BaseDBTest {

    private static final Log LOG = LogFactory.getLog(CommonDaoImplTest.class);

    @Autowired
    private CommonDao commonDao;




    @Test
    public void get() throws Exception {
        LOG.info("test.get");
        ClienteEntity clienteEntity = commonDao.get(ClienteEntity.class, 1l);
        Assert.assertNotNull(clienteEntity);
        Assert.assertTrue(clienteEntity.getId() == 1l);

        clienteEntity = commonDao.get(ClienteEntity.class, 2l);
        Assert.assertNotNull(clienteEntity);
        Assert.assertTrue(clienteEntity.getId() == 2l);

        clienteEntity = commonDao.get(ClienteEntity.class, 3l);
        Assert.assertNotNull(clienteEntity);
        Assert.assertTrue(clienteEntity.getId() == 3l);

    }

    @Test
    public void listAll() throws Exception {
        LOG.info("test.listAll");
        List<ClienteEntity> resultList = commonDao.listAll(ClienteEntity.class);
        Assert.assertNotNull(resultList);
        Assert.assertTrue(resultList.size() == 3);
    }

    @Test
    public void save() throws Exception {
        LOG.info("test.save");
    }

    @Test
    public void findByNativeQuery() throws Exception {
        LOG.info("test.findByNativeQuery");
    }

    @Test
    public void findByProperties() throws Exception {
        LOG.info("test.findByProperties");
    }

    @Test
    public void findByPropertiesSingleResult() throws Exception {
        LOG.info("test.findByPropertiesSingleResult");
    }

    @Test
    public void remove() throws Exception {
        LOG.info("test.remove");
    }

    @Test
    public void findByNativeQuery1() throws Exception {
        LOG.info("test.findByNativeQuery1");
    }

    @Test
    public void executeUpdate() throws Exception {
        LOG.info("test.executeUpdate");
    }

}
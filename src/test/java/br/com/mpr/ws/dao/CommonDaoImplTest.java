package br.com.mpr.ws.dao;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.entity.TestEntity;
import br.com.mpr.ws.utils.DateUtils;
import br.com.mpr.ws.vo.TestVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
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
        TestEntity testEntity = commonDao.get(TestEntity.class, 1l);
        Assert.assertNotNull(testEntity);
        Assert.assertTrue(testEntity.getId() == 1l);
        Assert.assertTrue(testEntity.getTestBoolean());
        Assert.assertTrue(testEntity.getTestVarchar().equals("123456789012345678901234512345678901234567890123451234567890123456789012345123456789012345678901234512345678901234567890123451234567890123456789012345123456789012345678901234512345678901234567890123451234567890123456789012345123456789012345678901234567890"));
        Assert.assertTrue(DateUtils.formatyyyyMMddTHHmmss(testEntity.getTestDate()).equals("30/09/1983 00:00:00"));
        Assert.assertTrue(DateUtils.formatyyyyMMddTHHmmss(testEntity.getTestDateTime()).equals("30/09/1983 19:10:02"));
        Assert.assertTrue(testEntity.getTestDouble() == 123456.12);
        Assert.assertTrue(testEntity.getTestInt() == 1234567890);
        Assert.assertTrue(testEntity.getTestLong() == 12345678901234l);


        //segundo teste para validar o boolean = false
        testEntity = commonDao.get(TestEntity.class, 2l);
        Assert.assertNotNull(testEntity);
        Assert.assertTrue(testEntity.getId() == 2l);
        Assert.assertFalse(testEntity.getTestBoolean());

    }

    @Test
    public void getError() throws Exception {
        LOG.info("test.getError");
        TestEntity testEntity = commonDao.get(TestEntity.class, 100000l);
        Assert.assertNull(testEntity);
    }

    @Test
    public void listAll() throws Exception {
        LOG.info("test.listAll");
        List<TestEntity> resultList = commonDao.listAll(TestEntity.class);
        Assert.assertNotNull(resultList);
        Assert.assertTrue(resultList.size() > 1);
    }

    @Test
    public void save() throws Exception {
        LOG.info("test.save");
        TestEntity testEntity = new TestEntity();
        testEntity.setTestBoolean(true);
        testEntity.setTestVarchar("RAMONES");
        testEntity.setTestDate(new Date());
        testEntity.setTestDateTime(new Date());
        testEntity.setTestDouble(45646.566666);
        testEntity.setTestInt(Integer.MAX_VALUE);
        testEntity.setTestLong(Long.MAX_VALUE);
        commonDao.save(testEntity);
        Assert.assertNotNull(testEntity.getId());
        Assert.assertTrue(testEntity.getTestVarchar().equals("RAMONES"));
    }

    @Test
    public void update() throws Exception {
        LOG.info("test.update");
        LOG.info("test.save");
        TestEntity testEntity = commonDao.get(TestEntity.class,2l);
        testEntity.setTestVarchar("Pneumoultramicroscopicossilicovulcanoconiótico");
        testEntity = commonDao.update(testEntity);
        Assert.assertTrue(testEntity.getTestVarchar().equals("Pneumoultramicroscopicossilicovulcanoconiótico"));
    }

    @Test
    public void findByNativeQueryEntity() throws Exception {
        LOG.info("test.findByNativeQueryEntity");
        List<TestEntity> listResult = commonDao.findByNativeQuery("Select * from TABLE_TEST ",
                TestEntity.class);
        Assert.assertTrue(listResult.size() > 0);
        for(TestEntity e : listResult){
            Assert.assertNotNull(e.getId());
            Assert.assertNotNull(e.getTestBoolean());
            Assert.assertNotNull(e.getTestDate());
            Assert.assertNotNull(e.getTestDateTime());
            Assert.assertNotNull(e.getTestDouble());
            Assert.assertNotNull(e.getTestInt());
            Assert.assertNotNull(e.getTestLong());
            Assert.assertNotNull(e.getTestVarchar());
        }

    }

    @Test
    public void findByNativeQueryVO() throws Exception {
        LOG.info("test.findByNativeQueryEntity");
        List<TestVo> listResult = commonDao.findByNativeQuery("Select * from TABLE_TEST",
                TestVo.class);
        Assert.assertTrue(listResult.size() > 0);
        for(TestVo vo : listResult){
            Assert.assertNotNull(vo.getId());
            Assert.assertNotNull(vo.getTestBoolean());
            Assert.assertNotNull(vo.getTestDate());
            Assert.assertNotNull(vo.getTestDateTime());
            Assert.assertNotNull(vo.getTestDouble());
            Assert.assertNotNull(vo.getTestInt());
            Assert.assertNotNull(vo.getTestLong());
            Assert.assertNotNull(vo.getTestVarchar());
        }
    }


    @Test
    public void findByProperties() throws Exception {
        LOG.info("test.findByProperties");
        List<TestEntity> listResult = commonDao.findByProperties(TestEntity.class, new String[]{"testInt","testBoolean"},
                new Object[]{1234567890,true});

        Assert.assertTrue(listResult.size() == 1);
        Assert.assertTrue(listResult.get(0).getId() == 1l);
    }

    @Test
    public void findByPropertiesSingleResult() throws Exception {
        LOG.info("test.findByPropertiesSingleResult");
        TestEntity testEntity = commonDao.findByPropertiesSingleResult(TestEntity.class, new String[]{"testInt","testBoolean"},
                new Object[]{1234567890,true});

        Assert.assertNotNull(testEntity);
        Assert.assertTrue(testEntity.getId() == 1l);
    }

    @Test
    public void remove() throws Exception {
        LOG.info("test.remove");
        TestEntity testEntity = commonDao.get(TestEntity.class,3l);
        Assert.assertNotNull(testEntity);
        Assert.assertTrue(testEntity.getTestVarchar().equals("REMOVER"));
        commonDao.remove(TestEntity.class,testEntity.getId());
        testEntity = commonDao.get(TestEntity.class,3l);
        Assert.assertNull(testEntity);
    }

    @Test
    public void findByNativeQueryWithParam() throws Exception {
        LOG.info("test.findByNativeQueryWithParam");
        List<TestEntity> listResult = commonDao.findByNativeQuery("Select * from TABLE_TEST where TEST_INT = ? ",
                TestEntity.class, 1234567890);
        Assert.assertTrue(listResult.size() > 0);
        for(TestEntity e : listResult){
            Assert.assertNotNull(e.getId());
            Assert.assertNotNull(e.getTestBoolean());
            Assert.assertNotNull(e.getTestDate());
            Assert.assertNotNull(e.getTestDateTime());
            Assert.assertNotNull(e.getTestDouble());
            Assert.assertNotNull(e.getTestInt());
            Assert.assertTrue(e.getTestInt() == 1234567890);
            Assert.assertNotNull(e.getTestLong());
            Assert.assertNotNull(e.getTestVarchar());
        }
    }

    @Test
    public void executeUpdate() throws Exception {
        LOG.info("test.executeUpdate");
        int exec = commonDao.executeUpdate("UPDATE TABLE_TEST SET TEST_INT = ? where TEST_INT = ?",
                123, 99999999);
        Assert.assertTrue(exec > 1);
        List<TestEntity> resultList = commonDao.findByNativeQuery("Select * from TABLE_TEST where TEST_INT = ?",
                TestEntity.class, 123);
        Assert.assertTrue(resultList.size() > 1);
        resultList = commonDao.findByNativeQuery("Select * from TABLE_TEST where TEST_INT = ?",
                TestEntity.class, 99999999);

        Assert.assertTrue(resultList.size() == 0);
    }

}

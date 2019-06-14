package br.com.mpr.ws;

import br.com.mpr.ws.config.TestsConfiguration;
import org.assertj.core.api.Fail;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("test")
@Import(TestsConfiguration.class)
public abstract class BaseDBTest implements ApplicationContextAware {

    @Autowired
    private LocalContainerEntityManagerFactoryBean factoryBean;

    @Value("classpath:test-data.sql")
    private Resource script;

    protected static ApplicationContext ac;

    private static boolean dbinit = false;


    @Before
    public void prepareDB() throws SQLException {
        if (!dbinit){
            //pegando o datasource
            EmbeddedDatabase dataSource = (EmbeddedDatabase)factoryBean.getDataSource();
            //criando um resource do script
            EncodedResource encodedScript = new EncodedResource(script, "UTF-8");
            //executando o script no H2 DB
            ScriptUtils.executeSqlScript(dataSource.getConnection(), encodedScript, true, true,
                    "--", ";", "/*", "*/");
            dbinit = true;
        }
    }



    @AfterClass
    public static void tearDown() {
        try {
            clearDatabase();
        } catch (Exception e) {
            Fail.fail(e.getMessage());
        }
    }

    public static void clearDatabase() throws SQLException {
        EmbeddedDatabase dataSource = (EmbeddedDatabase)
                ac.getBean(LocalContainerEntityManagerFactoryBean.class)
                        .getDataSource();
        Connection c = dataSource.getConnection();
        Statement s = c.createStatement();

        // Disable FK
        s.execute("SET REFERENTIAL_INTEGRITY FALSE");

        // Find all tables and truncate them
        Set<String> tables = new HashSet<String>();
        ResultSet rs = s.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES  where TABLE_SCHEMA='PUBLIC'");
        while (rs.next()) {
            tables.add(rs.getString(1));
        }
        rs.close();
        for (String table : tables) {
            s.executeUpdate("TRUNCATE TABLE " + table);
        }

        // Idem for sequences
        Set<String> sequences = new HashSet<String>();
        rs = s.executeQuery("SELECT SEQUENCE_NAME FROM INFORMATION_SCHEMA.SEQUENCES WHERE SEQUENCE_SCHEMA='PUBLIC'");
        while (rs.next()) {
            sequences.add(rs.getString(1));
        }
        rs.close();
        for (String seq : sequences) {
            s.executeUpdate("ALTER SEQUENCE " + seq + " RESTART WITH 1");
        }

        // Enable FK
        s.execute("SET REFERENTIAL_INTEGRITY TRUE");
        s.close();
        c.close();
        dbinit = false;
    }


    @Override
    public void setApplicationContext(ApplicationContext ac) {
        this.ac = ac;
    }

}

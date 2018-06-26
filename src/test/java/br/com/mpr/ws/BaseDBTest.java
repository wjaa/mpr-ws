package br.com.mpr.ws;

import br.com.mpr.ws.config.TestsConfiguration;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestsConfiguration.class)
public class BaseDBTest {

    @Autowired
    private LocalContainerEntityManagerFactoryBean factoryBean;

    @Value("classpath:test-data.sql")
    private Resource script;

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

}

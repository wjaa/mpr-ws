package br.com.mpr.ws;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Map;
import java.util.Properties;

/**
 * Created by wagner on 6/22/18.
 */
@Configuration
@ComponentScan(basePackages = "br.com.mpr")
@EnableTransactionManagement
//@ImportResource(value = {"classpath:queries/test-queries.xml"})
public class TestsConfiguration {

    @Bean
    public EmbeddedDatabase dataSource(){
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        builder.setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                //.addScript("classpath:schema.sql")
                //.addScript("classpath:test-data.sql")
                ;

        return builder.build();

    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("br.com.mpr");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        return em;
    }

  /*  @Bean
    public SessionFactory getSessionFactory(){
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(this.dataSource());
        sessionFactory.setPackagesToScan("br.com.mpr");
        sessionFactory.setHibernateProperties(additionalProperties());


        try {
            sessionFactory.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionFactory.getObject();
    }*/


    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

   /* @Bean
    public HibernateTransactionManager transactionManager(){
        HibernateTransactionManager htm = new HibernateTransactionManager(this.getSessionFactory());

        return htm;
    }*/


    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect","org.hibernate.dialect.H2Dialect");
        properties.setProperty("hibernate.show_sql","true");
        properties.setProperty("hibernate.format_sql","true");
        properties.setProperty("hibernante.hbm2ddl.auto", "create");
        properties.setProperty("hibernate.archive.autodetection","class");
        return properties;
    }

}

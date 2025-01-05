package com.amitnehra.configuration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.amitnehra")
@EnableTransactionManagement(proxyTargetClass = true)
public class HibernateConfig {
    private static final Logger logger = LogManager.getLogger(HibernateConfig.class.getName());

    @Bean(name = "sessionFactory")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.amitnehra");
        sessionFactory.setHibernateProperties(hibernateProperties());
        sessionFactory.setAnnotatedClasses();
        return sessionFactory;
    }


    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        Properties properties = hibernateProperties();
        dataSource.setDriverClassName((String) properties.get("hibernate.connection.driver_class"));
        dataSource.setUrl((String) properties.get("hibernate.connection.url"));
        dataSource.setUsername((String) properties.get("hibernate.connection.username"));
        dataSource.setPassword((String) properties.get("hibernate.connection.password"));
        return dataSource;
    }

    private Properties hibernateProperties() {
        InputStream input = getClass().getClassLoader().getResourceAsStream("hibernate_postgres.properties");

        Properties properties = new Properties();
        try {
            properties.load(input);
        } catch (IOException e) {
            logger.error(e);
        }
        return properties;
    }

    @Bean(name="transactionManager")
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager(sessionFactory);
        hibernateTransactionManager.setNestedTransactionAllowed(true);
        return hibernateTransactionManager;
    }
}

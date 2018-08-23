package com.cloudbank.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan({"com.cloudbank"})
@PropertySource(value = {"classpath:application.properties"})
@EnableJpaRepositories(basePackages = "com.cloudbank.repository",
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager")
public class JpaConfig {
    private static final String PROPERTY_DRIVER = "jdbc.driverClassName";
    private static final String PROPERTY_URL = "jdbc.url";
    private static final String PROPERTY_USERNAME = "jdbc.username";
    private static final String PROPERTY_PASSWORD = "jdbc.password";
    private static final String PROPERTY_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_FORMAT_SQL = "hibernate.format_sql";
    private static final String PROPERTY_MAPPINGS = "hibernate.id.new_generator_mappings";
    private static final String PACKAGE_WITH_MODEL = "entitymanager.packages.to.scan";

    @Autowired
    private Environment environment;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean managerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        managerFactoryBean.setDataSource(dataSource());
        managerFactoryBean.setPackagesToScan(environment.getRequiredProperty(PACKAGE_WITH_MODEL));
        managerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        managerFactoryBean.setJpaProperties(jpaProperties());
        return managerFactoryBean;
    }

    @Bean
    public DataSource dataSource() {
        DataSourceProperties dataSource = new DataSourceProperties();
        dataSource.setDriverClassName(environment.getProperty(PROPERTY_DRIVER));
        dataSource.setUrl(environment.getProperty(PROPERTY_URL));
        dataSource.setUsername(environment.getProperty(PROPERTY_USERNAME));
        dataSource.setPassword(environment.getProperty(PROPERTY_PASSWORD));
        return hikariDataSource(dataSource);
    }

    private HikariDataSource hikariDataSource(DataSourceProperties dataSource) {
        return DataSourceBuilder
                .create(dataSource.getClassLoader())
                .driverClassName(dataSource.getDriverClassName())
                .url(dataSource.getUrl())
                .username(dataSource.getUsername())
                .password(dataSource.getPassword())
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public Properties jpaProperties() {
        Properties properties = new Properties();
        properties.put(PROPERTY_DIALECT, environment.getRequiredProperty(PROPERTY_DIALECT));
        properties.put(PROPERTY_SHOW_SQL, environment.getRequiredProperty(PROPERTY_SHOW_SQL));
        properties.put(PROPERTY_FORMAT_SQL, environment.getRequiredProperty(PROPERTY_FORMAT_SQL));
        properties.put(PROPERTY_MAPPINGS, environment.getRequiredProperty(PROPERTY_MAPPINGS));
        return properties;
    }

    @Bean
    @Autowired
    public JpaTransactionManager transactionManager(EntityManagerFactory managerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(managerFactory);
        return transactionManager;
    }
}

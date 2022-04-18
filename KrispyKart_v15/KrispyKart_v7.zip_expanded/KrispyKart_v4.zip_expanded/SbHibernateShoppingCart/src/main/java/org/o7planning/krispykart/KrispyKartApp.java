package org.o7planning.krispykart;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@SpringBootApplication

@EnableAutoConfiguration(exclude = { 
        DataSourceAutoConfiguration.class, 
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class })

public class KrispyKartApp extends SpringBootServletInitializer {

	// allows for Spring to resolve and inject collaborating beans into this bean using Environment Env
    @Autowired
    private Environment env;
    
    // Main method used to run and initiate the spring application
    public static void main(String[] args) {
        SpringApplication.run(KrispyKartApp.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(KrispyKartApp.class);
    }
    
    
    @Bean(name = "dataSource")
    public DataSource getDataSource() { // constructor used for Bean style configuration
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        // From application.properties: used to set the driver class name
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        
        // From application.properties: set the URL
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        
        // From application.properties: Set the users username
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        
        // From application.properties: Set the users password
        dataSource.setPassword(env.getProperty("spring.datasource.password"));

        System.out.println("## getDataSource: " + dataSource);

        return dataSource;
    }

    @Autowired
    @Bean(name = "sessionFactory") // declaration of Bean
    public SessionFactory getSessionFactory(DataSource dataSource) throws Exception {
        Properties properties = new Properties();

        // From application.properties: collect the JPA/hibernate dialect
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        
        // From application.properties: collect the JPA/hibernate SQL file
        properties.put("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
        
        // From application.properties: collect the JPA/hibernate context class from the current session
        properties.put("current_session_context_class", env.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));

        //FactoryBean used to create a Hibernate SessionFactory
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

        // Package contain entity classes
        factoryBean.setPackagesToScan(new String[] { "" });
        factoryBean.setDataSource(dataSource);
        factoryBean.setHibernateProperties(properties);
        factoryBean.afterPropertiesSet();
        
        SessionFactory sf = factoryBean.getObject();
        System.out.println("## getSessionFactory: " + sf);
        return sf;
    }

    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
    	
    	// Create a new HibernateTransactionManager instance and return the instance
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);

        return transactionManager;
    }
}
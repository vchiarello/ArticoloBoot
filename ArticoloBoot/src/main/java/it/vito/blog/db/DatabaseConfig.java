package it.vito.blog.db;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class DatabaseConfig {

	@Value("${databaseDriver}")
	String databaseDriver;
	
	@Value("${databaseUrl}")
	String databaseUrl;
	
	@Value("${databaseUser}")
	String databaseUser;
	
	@Value("${databasePassword}")
	String databasePassword;
	
	@Bean
	public DataSource dataSource() {
		  	
		DriverManagerDataSource d = new DriverManagerDataSource();
		d.setDriverClassName(databaseDriver);
		d.setUrl(databaseUrl);
		d.setUsername(databaseUser);
		d.setPassword(databasePassword);
		return d;
			  
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean  entityManagerFactory() {
	
	    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	    //vendorAdapter.setGenerateDdl(true);
	    vendorAdapter.setDatabasePlatform("org.hibernate.dialect.SQLServer2008Dialect");
	    
	    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	    factory.setJpaVendorAdapter(vendorAdapter);
	    factory.setPackagesToScan("it.vito.blog.db");
	    factory.setDataSource(dataSource());
	    //factory.se
	    factory.afterPropertiesSet();
	
	    
	    return factory;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {

	    JpaTransactionManager txManager = new JpaTransactionManager();
	    txManager.setEntityManagerFactory(entityManagerFactory().getObject());
	    return txManager;
	}
	
}

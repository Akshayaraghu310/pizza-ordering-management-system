package com.example.pizzadetails.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@RefreshScope
public class PropertiesConfigServer {
	@Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    @Value("${spring.datasource.driver-class-name:org.postgresql.Driver}")
    private String datasourceDriverClassName;

   // @Value("${spring.jpa.properties.hibernate.dialect:org.hibernate.dialect.PostgreSQLDialect}")
    //private String hibernateDialect;
    
    @Value("${pizzaDetails.service.message:Default message from PizzaDetails Service}")
    //@Value("${pizzaDetails.service.message}")
    private String serviceMessage;

   /* @Bean(name = "customDataSource")
    DataSource dataSource() {
    	return DataSourceBuilder.create()
                .url(datasourceUrl)
                .username(datasourceUsername)
                .password(datasourcePassword)
                .driverClassName(datasourceDriverClassName)
                .build();
    }
    */
    public String getServiceMessage() {
        return serviceMessage;
    }
}

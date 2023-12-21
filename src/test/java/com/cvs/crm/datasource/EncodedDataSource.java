package com.cvs.crm.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Base64;

@Configuration
public class EncodedDataSource {

    private String decode(String password) {
        Base64.Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(password));
    }

    @Value("${spring.datasource.driver-class-name}")
    private String driverName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource getDataSource() {
        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverName);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(decode(password));
        
        return dataSource;
    }
}

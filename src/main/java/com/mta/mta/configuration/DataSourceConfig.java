package com.mta.mta.configuration;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();

        // 1. Load master DB (fixed)
        DataSource masterDataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/master_db")
                .username("root")
                .password("")
                .build();

        targetDataSources.put("master", masterDataSource);

        // 2. (Optional now) Load existing tenants from DB if needed
        // You can load dynamic tenants here at runtime later if needed

        MultiTenantDataSource dataSource = new MultiTenantDataSource();
        dataSource.setDefaultTargetDataSource(masterDataSource);
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.afterPropertiesSet();

        return dataSource;
    }
}

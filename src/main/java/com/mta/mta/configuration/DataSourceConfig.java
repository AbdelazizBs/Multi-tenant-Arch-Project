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

    private static final Map<Object, Object> targetDataSources = new HashMap<>();
    public static MultiTenantDataSource multiTenantDataSource = new MultiTenantDataSource();

    static {
        // Initialize with a default DataSource (e.g., master database)
        DataSource defaultDataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/master_db")
                .username("root")
                .password("")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
        targetDataSources.put("master", defaultDataSource);
        multiTenantDataSource.setTargetDataSources(targetDataSources);
        multiTenantDataSource.setDefaultTargetDataSource(defaultDataSource);
        multiTenantDataSource.afterPropertiesSet();
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        return multiTenantDataSource;
    }

    public static void addTenantDataSource(String tenantId, String dbUrl) {
        DataSource tenantDataSource = DataSourceBuilder.create()
                .url(dbUrl)
                .username("root")
                .password("")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
        targetDataSources.put(tenantId, tenantDataSource);
        multiTenantDataSource.setTargetDataSources(targetDataSources);
        multiTenantDataSource.afterPropertiesSet();
    }
}

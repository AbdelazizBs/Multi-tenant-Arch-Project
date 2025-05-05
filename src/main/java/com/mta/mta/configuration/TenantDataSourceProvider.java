package com.mta.mta.configuration;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TenantDataSourceProvider {

    private final ConcurrentHashMap<String, DataSource> tenantDataSources = new ConcurrentHashMap<>();

    public DataSource getOrCreateDataSource(String tenantId) {
        return tenantDataSources.computeIfAbsent(tenantId, this::createDataSourceForTenant);
    }

    private DataSource createDataSourceForTenant(String tenantId) {
        String url = "jdbc:mysql://localhost:3306/" + tenantId;
        return DataSourceBuilder.create()
                .url(url)
                .username("root")
                .password("")
                .build();
    }
}
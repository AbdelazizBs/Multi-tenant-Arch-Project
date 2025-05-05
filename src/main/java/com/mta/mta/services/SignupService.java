package com.mta.mta.services;

import com.mta.mta.configuration.DataSourceConfig;
import com.mta.mta.entity.SignupEntity;
import com.mta.mta.repository.SignupRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class SignupService {

    private final SignupRepository signupRepository;
    private final JdbcTemplate jdbcTemplate;

    public SignupService(SignupRepository signupRepository, JdbcTemplate jdbcTemplate) {
        this.signupRepository = signupRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public SignupEntity registerUser(SignupEntity signup) {
        SignupEntity savedUser = signupRepository.save(signup);

        String dbName = signup.getWebAddress().replaceAll("[^a-zA-Z0-9_]", "_");
        createTenantDatabase(dbName);
        createProjectTable(dbName);

        String dbUrl = "jdbc:mysql://localhost:3306/" + dbName;
        DataSourceConfig.addTenantDataSource(dbName, dbUrl);

        return savedUser;
    }

    private void createTenantDatabase(String dbName) {
        String createDbSql = "CREATE DATABASE IF NOT EXISTS `" + dbName + "`";
        jdbcTemplate.execute(createDbSql);
    }

    private void createProjectTable(String dbName) {
        String createTableSql = """
            CREATE TABLE IF NOT EXISTS projects (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255),
                description VARCHAR(255)
            );
        """;

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, "root", "");
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSql);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating project table for tenant: " + dbName, e);
        }
    }

    private String formatDbName(String webAddress) {
        return webAddress.replaceAll("[^a-zA-Z0-9_]", "_");
    }





    public void softDeleteUser(Long id) {
        SignupEntity user = signupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setDeleted(true);
        signupRepository.save(user);
    }

    public void hardDeleteUser(Long id) {
        SignupEntity user = signupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String dbName = formatDbName(user.getWebAddress());

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP DATABASE IF EXISTS `" + dbName + "`");
        } catch (SQLException e) {
            throw new RuntimeException("Error dropping tenant database", e);
        }

        signupRepository.delete(user);
    }
}

package com.mta.mta.services;


import com.mta.mta.entity.SignupEntity;
import com.mta.mta.repository.SignupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
        // Save user in master DB
        SignupEntity savedUser = signupRepository.save(signup);

        // Create tenant DB (e.g., use webAddress as DB name)
        String dbName = signup.getWebAddress().replaceAll("[^a-zA-Z0-9_]", "_");
        createTenantDatabase(dbName);

        return savedUser;
    }

    private void createTenantDatabase(String dbName) {
        String createDbSql = "CREATE DATABASE IF NOT EXISTS `" + dbName + "`";
        jdbcTemplate.execute(createDbSql);
        // You can later initialize schema/tables here if needed
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

        String dbName = user.getWebAddress();

        // Drop tenant DB (MySQL-specific)
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP DATABASE IF EXISTS `" + dbName + "`");
        } catch (SQLException e) {
            throw new RuntimeException("Error dropping tenant database", e);
        }

        // Delete from master
        signupRepository.delete(user);
    }
}


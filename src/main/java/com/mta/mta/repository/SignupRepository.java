package com.mta.mta.repository;

import com.mta.mta.entity.SignupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignupRepository extends JpaRepository<SignupEntity, Long> {
    boolean existsByEmail(String email);
    boolean existsByWebAddress(String webAddress);
}
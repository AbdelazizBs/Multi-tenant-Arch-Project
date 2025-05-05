package com.mta.mta.controller;

import com.mta.mta.entity.SignupEntity;
import com.mta.mta.services.SignupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/signup")
public class SignupController {

    private final SignupService signupService;

    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }
    @PostMapping
    public ResponseEntity<SignupEntity> registerUser(@RequestBody SignupEntity signup) {
        SignupEntity savedUser = signupService.registerUser(signup);
        return ResponseEntity.ok(savedUser);
    }

    @DeleteMapping("/{id}/soft")
    public ResponseEntity<String> softDelete(@PathVariable Long id) {
        signupService.softDeleteUser(id);
        return ResponseEntity.ok("User soft-deleted");
    }

    @DeleteMapping("/{id}/hard")
    public ResponseEntity<String> hardDelete(@PathVariable Long id) {
        signupService.hardDeleteUser(id);
        return ResponseEntity.ok("User hard-deleted and DB dropped");
    }
}
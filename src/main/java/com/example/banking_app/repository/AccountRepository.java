package com.example.banking_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.banking_app.entitiy.Account;

public interface AccountRepository extends JpaRepository <Account, Long> {
    
}
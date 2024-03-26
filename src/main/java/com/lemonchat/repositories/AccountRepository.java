package com.lemonchat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lemonchat.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}

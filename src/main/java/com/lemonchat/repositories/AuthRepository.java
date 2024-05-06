package com.lemonchat.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lemonchat.entities.Auth;

public interface AuthRepository extends JpaRepository<Auth, Long> {
	Optional<Auth> findByAccountId(Long accountId);
}

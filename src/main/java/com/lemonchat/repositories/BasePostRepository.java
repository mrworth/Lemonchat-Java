package com.lemonchat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lemonchat.entities.BasePost;

public interface BasePostRepository extends JpaRepository<BasePost, Long> {
    
}
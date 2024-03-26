package com.lemonchat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lemonchat.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    
}
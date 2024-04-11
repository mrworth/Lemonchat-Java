package com.lemonchat.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lemonchat.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	Set<Post> findByParentPostIsNullAndPostIdNotIn(List<Long> excludedPostIds);
	boolean existsByTopic(String topic);
}

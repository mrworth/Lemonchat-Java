package com.lemonchat.services;

import java.util.List;
import java.util.Set;

import com.lemonchat.dtos.BasePostDto;
import com.lemonchat.dtos.PostDto;

public interface PostService {
//    List<PostDto> findPostsByAccountName(String accountName);
//    List<PostDto> findRandomRepliesByPostId(Long postId);
	BasePostDto findBasePostById(Long postId);
	
	PostDto findPostByBasePost(BasePostDto basePostDto);
	PostDto createPost(PostDto postDto);
	PostDto updatePost(PostDto postDto);
	void deletePost(Long postId);
	PostDto findPostById(Long postId);

	Set<PostDto> getTopicsNotInList(List<Long> topicIds);
}
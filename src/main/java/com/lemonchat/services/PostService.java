package com.lemonchat.services;

import com.lemonchat.dtos.PostDto;

public interface PostService {
//    List<PostDto> findPostsByAccountName(String accountName);
//    List<PostDto> findRandomRepliesByPostId(Long postId);
	PostDto createPost(PostDto postDto, Long inReplyTo);
	PostDto updatePost(PostDto postDto);
	void deletePost(Long postId);
	PostDto findPostById(Long postId);
}
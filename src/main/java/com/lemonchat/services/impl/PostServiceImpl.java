package com.lemonchat.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lemonchat.dtos.PostDto;
import com.lemonchat.entities.Post;
import com.lemonchat.mappers.PostMapper;
import com.lemonchat.repositories.PostRepository;
import com.lemonchat.services.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

//    @Override
//    public List<PostDto> findPostsByAccountName(String accountName) {
//        return postRepository.findByAccount_Name(accountName).stream()
//                .map(PostMapper.INSTANCE::postToPostDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<PostDto> findRandomRepliesByPostId(Long postId) {
//        return postRepository.findRandomRepliesByPostId(postId).stream()
//                .map(PostMapper.INSTANCE::postToPostDto)
//                .collect(Collectors.toList());
//    }

	@Override
	public PostDto createPost(PostDto postDto,Long inReplyTo) {
		Post post = PostMapper.INSTANCE.postDtoToPost(postDto);
		if(inReplyTo!=null) {
			Post parentPost = postRepository.findById(inReplyTo)
					.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent post not found with id " + inReplyTo));
			post.setParentPost(parentPost);
		}
		return PostMapper.INSTANCE.postToPostDto(postRepository.save(post));
	}

	@Override
	public PostDto updatePost(PostDto postDto) {
	    Post existingPost = postRepository.findById(postDto.getPostId())
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id " + postDto.getPostId()));
	    existingPost.setContent(postDto.getContent());
	    return PostMapper.INSTANCE.postToPostDto(postRepository.saveAndFlush(existingPost));
	}

	@Override
	public void deletePost(Long postId) {
		postRepository.deleteById(postId);
	}

	@Override
	public PostDto findPostById(Long postId) {
		Post existingPost =  postRepository.findById(postId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id " + postId));
		return PostMapper.INSTANCE.postToPostDto(existingPost);
	}
}
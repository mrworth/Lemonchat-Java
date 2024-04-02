package com.lemonchat.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lemonchat.dtos.BasePostDto;
import com.lemonchat.dtos.PostDto;
import com.lemonchat.entities.Account;
import com.lemonchat.entities.BasePost;
import com.lemonchat.entities.Post;
import com.lemonchat.mappers.PostMapper;
import com.lemonchat.repositories.AccountRepository;
import com.lemonchat.repositories.BasePostRepository;
import com.lemonchat.repositories.PostRepository;
import com.lemonchat.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
    private PostMapper postMapper;
	
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private BasePostRepository basePostRepository;
    
    @Autowired
    private PostRepository postRepository;
    
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

//    @Override
//    public List<PostDto> findPostsByAccountName(String accountName) {
//        return postRepository.findByAccount_Name(accountName).stream()
//                .map(postMapper::postToPostDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<PostDto> findRandomRepliesByPostId(Long postId) {
//        return postRepository.findRandomRepliesByPostId(postId).stream()
//                .map(postMapper::postToPostDto)
//                .collect(Collectors.toList());
//    }

	@Override
	public BasePostDto findBasePostById(Long postId) {
		BasePost existingPost =  basePostRepository.findById(postId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id " + postId));
		return postMapper.postToPostDto(existingPost);
	}

	@Override
	public PostDto createPost(PostDto postDto) {
		Post post = postMapper.postDtoToPost(postDto);
		Long inReplyTo = postDto.getInReplyTo();
		String username = postDto.getUsername();
		Account account = accountRepository.findByUsername(username).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found with username " + username));
		post.setAccount(account);
		//inReplyTo is valid as null (this is a topic)
		if(inReplyTo!=null) {
			BasePost parentPost = basePostRepository.findById(inReplyTo)
					.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent post not found with id " + inReplyTo));
			post.setParentPost(parentPost);
			post.setTopic(parentPost.getTopic());
		}
		return postMapper.postToPostDto(postRepository.save(post));
	}

	@Override
	public PostDto updatePost(PostDto postDto) {
	    Post existingPost = postRepository.findById(postDto.getPostId())
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id " + postDto.getPostId()));
	    existingPost.setContent(postDto.getContent());
	    existingPost.setTitle(postDto.getTitle());
	    return postMapper.postToPostDto(postRepository.saveAndFlush(existingPost));
	}

	@Override
	public void deletePost(Long postId) {
		postRepository.deleteById(postId);
	}

	@Override
	public PostDto findPostById(Long postId) {
		Post existingPost =  postRepository.findById(postId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id " + postId));
		return postMapper.postToPostDto(existingPost);
	}

}
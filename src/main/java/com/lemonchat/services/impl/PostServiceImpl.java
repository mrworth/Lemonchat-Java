package com.lemonchat.services.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

import jakarta.transaction.Transactional;

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

	@Override
	public BasePostDto findBasePostById(Long postId) {
		BasePost existingPost =  basePostRepository.findById(postId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id " + postId));
		return postMapper.postToPostDto(existingPost);
	}
	
	@Override
	public PostDto findPostByBasePost(BasePostDto basePostDto) {
		return postMapper.postToPostDto(postRepository.findById(basePostDto.getPostId()).get());
	}
	

	@Override
	@Transactional
	public PostDto createPost(PostDto postDto) {
		Post post = postMapper.postDtoToPost(postDto);
		post.setHasReplies(false);
		Long inReplyTo = postDto.getInReplyTo();
		String username = postDto.getUsername();
		Account account = accountRepository.findByUsername(username).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found with username " + username));
		post.setAccount(account);
		//inReplyTo is valid as null (this is a topic)
		if(inReplyTo == null) {
			//should consider lookup table for topics (uniqueness constraint for new topics not strongly enforced on db side - concurrency issue: low concern for POC)
			if(postRepository.existsByTopic(postDto.getTopic())) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "Topic already exists for " + postDto.getTopic());
			}
			post.setTitle(post.getTopic());
			return postMapper.postToPostDto(postRepository.save(post));
		}
		BasePost parentBasePost = basePostRepository.findById(inReplyTo)
				.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent post not found with id " + inReplyTo));
		post.setParentPost(parentBasePost);
		post.setTopic(parentBasePost.getTopic());
		PostDto returnPost = postMapper.postToPostDto(postRepository.save(post));
		if(parentBasePost!=null && !parentBasePost.getHasReplies()) {
			Post parentPost = postRepository.findById(inReplyTo).get();
			parentPost.setHasReplies(true);
			postRepository.save(parentPost);
		}
		return returnPost;
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
	@Transactional
	public void deletePost(Long postId) {
		Post post = postRepository.findById(postId).get();
		Long inReplyTo = post.getInReplyTo();
		if(inReplyTo!=null) {			
			Post parentPost = postRepository.findById(inReplyTo).get();
			if(parentPost.getReplies().size()==1) {				
				parentPost.setHasReplies(false);
				postRepository.save(parentPost);
			}
		}
		postRepository.deleteById(postId);
	}

	@Override
	public PostDto findPostById(Long postId) {
		Post existingPost =  postRepository.findById(postId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id " + postId));
		return postMapper.postToPostDto(existingPost);
	}

	@Override
	public Set<PostDto> getTopicsNotInList(List<Long> topicIds) {
		if(topicIds.size()==0) {
			topicIds.add(-1L);
		}
		Set<Post> posts = postRepository.findByParentPostIsNullAndPostIdNotIn(topicIds);
		return posts.stream()
                .map(postMapper::postToPostDto)
                .collect(Collectors.toSet());
	}

}
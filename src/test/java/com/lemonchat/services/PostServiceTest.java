package com.lemonchat.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
import com.lemonchat.services.impl.PostServiceImpl;

@ExtendWith(SpringExtension.class)
public class PostServiceTest {
	
	@Mock
    private PostRepository postRepository;
	
	@Mock
    private BasePostRepository basePostRepository;
	
	@Mock
    private AccountRepository accountRepository;
	
	@Mock
    private PostMapper postMapper;
	
	@Mock
    private BasePost basePostMock = mock(BasePost.class);
	
	@InjectMocks
	PostServiceImpl postService = new PostServiceImpl();
	
	@Test
	public void testFindBasePostById() {
		BasePost basePost = new BasePost();
		BasePostDto basePostDto = new BasePostDto();
		given(basePostRepository.findById(1L)).willReturn(Optional.of(basePost));
		given(postMapper.postToPostDto(basePost)).willReturn(basePostDto);
		
		postService.findBasePostById(1L);
		then(postMapper).should(times(1)).postToPostDto(basePost);
	}
	
	public PostDto setupCreatePost(Post post,String incomingUsername, String storedUsername) {
		post.setUsername(incomingUsername);
		PostDto postDto = new PostDto();
		postDto.setUsername(incomingUsername);
		Account account = new Account(1L,storedUsername,"test@test.com");
		
		given(accountRepository.findByUsername(storedUsername)).willReturn(Optional.of(account));
		given(postRepository.saveAndFlush(post)).willReturn(post);
		
		given(postMapper.postToPostDto(post)).willReturn(postDto);
		given(postMapper.postDtoToPost(postDto)).willReturn(post);
		return postDto;
	}
	
	@Test
	public void testCreatePost() {
		Post post = new Post();
		PostDto postDto = setupCreatePost(post,"testusername","testusername");
        postService.createPost(postDto);
        then(postRepository).should(times(1)).save(post);
	}
	
	@Test
	public void testCreatePostUsernameNotFound() {
		Post post = new Post();
		PostDto postDto = setupCreatePost(post,"wrongusername","storedUsername");
        assertThrows(ResponseStatusException.class, () -> {
        	postService.createPost(postDto);
        });
	}
	
	@Test
	public void testCreatePostWithParent() {
		Post post = new Post();
		post.setInReplyTo(1L);
		PostDto postDto = setupCreatePost(post,"testusername","testusername");
		postDto.setInReplyTo(1L);
		Post parentPostMock = mock(Post.class);
		parentPostMock.setHasReplies(false);
		
		given(basePostRepository.findById(1L)).willReturn(Optional.of(basePostMock));
		given(postRepository.findById(1L)).willReturn(Optional.of(parentPostMock));
		
		postService.createPost(postDto);
		then(postRepository).should(times(2)).save(any(Post.class));
		then(basePostMock).should(times(1)).getTopic();
	}
	
	@Test
	public void testGetPost() {
		Post post = new Post();
		PostDto postDto = new PostDto();
		
		given(postRepository.findById(1L)).willReturn(Optional.of(post));
		
		given(postMapper.postToPostDto(post)).willReturn(postDto);
        given(postMapper.postDtoToPost(postDto)).willReturn(post);
        
        postService.findPostById(1L);
        then(postRepository).should(times(1)).findById(1L);  
	}
	
	public PostDto setupUpdatePost(Post post,Long postId) {
		post.setPostId(postId);
		PostDto postDto = new PostDto();
		postDto.setPostId(postId);
		
		given(postRepository.findById(postId)).willReturn(Optional.of(post));
		given(postRepository.saveAndFlush(post)).willReturn(post);
		
		given(postMapper.postDtoToPost(postDto)).willReturn(post);
		return postDto;
	}
	
	@Test
	public void testUpdatePost() {
		Post post = new Post();
		PostDto postDto = setupUpdatePost(post, 1L);
		
		postService.updatePost(postDto);
		then(postRepository).should(times(1)).saveAndFlush(post);
	} 
	
	@Test
	public void testUpdatePostDNE() {
		Post post = new Post();
		PostDto postDto = setupUpdatePost(post, 1L);
		
		given(postRepository.findById(1L)).willThrow(ResponseStatusException.class);
		
		assertThrows(ResponseStatusException.class, ()->{				
			postService.updatePost(postDto);
		});
	} 
	
	@Test
	public void testDeletePost() {
		Post post = new Post();
		post.setInReplyTo(1L);
		BasePost basePost = mock(BasePost.class);
		List<BasePost> postReplies = new ArrayList<BasePost>();
		postReplies.add(basePost);
		Post parentPost = new Post();
		parentPost.setPostId(1L);
		parentPost.setReplies(postReplies);
		post.setParentPost(parentPost);
		given(postRepository.findById(2L)).willReturn(Optional.of(post));
		given(postRepository.findById(1L)).willReturn(Optional.of(parentPost));
		postService.deletePost(2L);
		then(postRepository).should(times(1)).save(any(Post.class));
		then(postRepository).should(times(1)).deleteById(2L);
	} 
}

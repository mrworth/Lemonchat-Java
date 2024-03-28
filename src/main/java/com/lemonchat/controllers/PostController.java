package com.lemonchat.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lemonchat.dtos.BasePostDto;
import com.lemonchat.dtos.PostDto;
import com.lemonchat.services.PostService;
import com.lemonchat.services.impl.PostServiceImpl;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;
    
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);
    
    @GetMapping("/basepost/{postId}")
    public BasePostDto getBasePostById(@PathVariable Long postId) {
        return postService.findBasePostById(postId);
    }
    
    @GetMapping("/{postId}")
    public PostDto getPostById(@PathVariable Long postId) {
        return postService.findPostById(postId);
    }


//    @GetMapping("/by-account/{accountName}")
//    public List<PostDto> getPostsByAccountName(@PathVariable String accountName) {
//        return postService.findPostsByAccountName(accountName);
//    }
//
//    @GetMapping("/{postId}/random-replies")
//    public List<PostDto> getRandomRepliesByPostId(@PathVariable Long postId) {
//        return postService.findRandomRepliesByPostId(postId);
//    }
    
    
    @PostMapping
    public ResponseEntity<PostDto> createPostWithReplies(@RequestBody PostDto requestDto) {
    	PostDto createdPost = postService.createPost(requestDto);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long postId, @RequestBody PostDto postDto) {
    	postDto.setPostId(postId); 
        PostDto updatedPost = postService.updatePost(postDto);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
    
}

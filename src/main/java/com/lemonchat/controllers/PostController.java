package com.lemonchat.controllers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lemonchat.dtos.BasePostDto;
import com.lemonchat.dtos.PostDto;
import com.lemonchat.entities.Post;
import com.lemonchat.services.PostService;
import com.lemonchat.services.impl.PostServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;
    
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);
    
    @GetMapping("/basepost/{postId}")
    public BasePostDto getBasePostById(@PathVariable("postId") Long postId) {
        return postService.findBasePostById(postId);
    }
    
    @GetMapping("/{postId}")
    public PostDto getPostById(@PathVariable("postId") Long postId) {
        return postService.findPostById(postId);
    }
    
    @GetMapping("/map/{postId}")
    public Set<BasePostDto> getPostMapById(@PathVariable("postId") Long postId, @RequestParam(name = "depth", required = false) Integer depth) {
    	PostDto parentPostDto = postService.findPostById(postId);
    	Set<PostDto> posts = new HashSet<PostDto>();
    	posts.add(parentPostDto);
    	getReplyPostsNesting(posts, new HashSet<Long>(), depth, 1);
        return postSetToBasePostSet(posts);
    }
    
    @GetMapping("/topics")
    public Set<BasePostDto> getTopics(@RequestParam("topic_ids") String topicIdsString) {
    	List<Long> topicIds = Arrays.stream(topicIdsString.split(","))
                .map(String::trim) 
                .map(Long::valueOf) 
                .collect(Collectors.toList());
    	Set<PostDto> topicPosts = postService.getTopicsNotInList(topicIds);
    	getReplyPostsNesting(topicPosts, new HashSet<Long>(), 3, 1);
    	return postSetToBasePostSet(topicPosts);
    }
    
    private Set<BasePostDto> postSetToBasePostSet(Set<PostDto> postDtos){
    	Set<BasePostDto> basePostDtos = new HashSet<BasePostDto>();
    	for(PostDto postDto : postDtos) {
    		basePostDtos.add(new BasePostDto(postDto));
    	}
    	return basePostDtos;
    }
    
    private boolean getReplyPostsNesting(Set<PostDto> allPosts, Set<Long> processedPostIds, int maxDepth, int currentDepth){
    	//if any post is processed, set to false and call this function again
    	boolean allPostsProcessed = true;
    	Set<PostDto> newPosts = new HashSet<PostDto>();
    	for(PostDto postDto : allPosts) {
    		if(processedPostIds.contains(postDto.getPostId())) {
    			continue;
    		}
    		if(postDto.getHasReplies()) {
    			Long postId = postDto.getPostId();
    			allPostsProcessed = false;
    			processedPostIds.add(postId);
    			for(BasePostDto basePostDto : postDto.getReplies()) {
    				if(basePostDto.getHasReplies()) {
    					newPosts.add(postService.findPostByBasePost(basePostDto));
    					continue;
    				}
    				newPosts.add(new PostDto(basePostDto, postId));
    			}
    		}
    	}
    	allPosts.addAll(newPosts);
    	if(allPostsProcessed || currentDepth >= maxDepth) {    		
    		return true;
    	}
    	return getReplyPostsNesting(allPosts,processedPostIds, maxDepth, currentDepth+1); 
    }
    
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto requestDto) {
    	PostDto createdPost = postService.createPost(requestDto);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto) {
        PostDto updatedPost = postService.updatePost(postDto);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
    
}

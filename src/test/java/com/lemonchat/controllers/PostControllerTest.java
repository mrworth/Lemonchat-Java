package com.lemonchat.controllers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemonchat.dtos.PostDto;
import com.lemonchat.services.PostService;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper; 
    
    public PostDto buildPostDto(String content, String title, String topic) {
    	PostDto postDto = new PostDto();
    	postDto.setContent(content);
    	postDto.setTitle(title);
    	postDto.setTopic(topic);
    	return postDto;
    }
    
    @Test
    public void testGetBasePost() throws Exception {
    	PostDto postDto = buildPostDto("","","");
    	given(postService.findBasePostById(1L)).willReturn(postDto);
        mockMvc.perform(get("/posts/basepost/{postId}", 1L))
                .andExpect(status().is2xxSuccessful());
    }
    
    @Test
    public void testGetPost() throws Exception {
    	PostDto postDto = buildPostDto("","","");
    	given(postService.findPostById(1L)).willReturn(postDto);
        mockMvc.perform(get("/posts/{postId}", 1L))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testCreatePost() throws Exception {
    	testCreatePostCreated(buildPostDto("","",""));
    	testCreatePostFailure(buildPostDto(null,"",""));
    	testCreatePostFailure(buildPostDto("",null,""));
    	testCreatePostFailure(buildPostDto("","",null));
    }
    
    
    public void testCreatePostCreated(PostDto postDto) throws Exception{
    	given(postService.createPost(postDto)).willReturn(postDto);
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isCreated());
    }
    
    public void testCreatePostFailure(PostDto postDto) throws Exception{
    	given(postService.createPost(postDto)).willReturn(postDto);
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().is4xxClientError());
    }
    
    @Test
    public void testUpdatePost() throws Exception {
    	
        PostDto postDto = mock(PostDto.class);
        postDto.setContent("Test Content");
        postDto.setTitle("test title");
        postDto.setAccount(null);
        
        given(postService.updatePost(postDto)).willReturn(postDto);
        mockMvc.perform(put("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk());
    }
    
    @Test
    public void testDeletePost() throws Exception {
    	doNothing().when(postService).deletePost(1L);
        mockMvc.perform(delete("/posts/{postId}",1L))
                .andExpect(status().isNoContent());
    }
}


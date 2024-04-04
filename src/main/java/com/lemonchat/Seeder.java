package com.lemonchat;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.lemonchat.dtos.AccountDto;
import com.lemonchat.dtos.PostDto;
import com.lemonchat.services.AccountService;
import com.lemonchat.services.PostService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class Seeder implements CommandLineRunner{
	
	private final AccountService accountService;
	private final PostService postService;
	
	@Override
	public void run(String... args) throws Exception {
		AccountDto accountDto = new AccountDto(null,"testusername","test@test.com");
		accountDto = accountService.createAccount(accountDto);
		PostDto postDto = new PostDto();
		postDto.setPostDetails(null,"Test Title", "Test Topic", "Test Content", "testusername");
		postDto = postService.createPost(postDto);
		createReply(postDto, "Test Title Reply", "Test Content Reply", "testusername");
		PostDto replyPostDto2 = createReply(postDto, "Test Title Reply 2", "Test Content Reply 2", "testusername");
		createReply(replyPostDto2, "Test Title Reply 2-1", "Test Content Reply 2-1", "testusername");
		createReply(replyPostDto2, "Test Title Reply 2-1-1", "Test Content Reply 2-1", "testusername");
	}
	
	private PostDto createReply(PostDto parentDto, String title, String content, String username) {
		PostDto replyDto = new PostDto();
		replyDto.setPostDetails(parentDto.getPostId(),title, parentDto.getTopic(), content, username);
		return postService.createPost(replyDto);
	}
}

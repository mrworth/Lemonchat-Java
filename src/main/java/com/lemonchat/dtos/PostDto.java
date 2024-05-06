package com.lemonchat.dtos;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(callSuper=true)
public class PostDto extends BasePostDto{
	
    
	
	private Set<BasePostDto> replies;
	
	public PostDto(BasePostDto basePostDto, Long inReplyTo) {
		if(basePostDto.getHasReplies()) {
			throw new IllegalArgumentException("BasePost has replies in database, constructor is invalid.");
		}
		this.setPostId(basePostDto.getPostId());
		this.setPostDetails(
				basePostDto.getInReplyTo(), 
				basePostDto.getTitle(), 
				basePostDto.getTopic(),
				basePostDto.getContent(),
				basePostDto.getUsername()
		);
		this.setHasReplies(false);
	}
	
	public void setPostDetails(Long inReplyTo,String title, String topic, String content, String username) {
		this.setInReplyTo(inReplyTo);
		this.setTitle(title);
		this.setTopic(topic);
		this.setContent(content);
		this.setUsername(username);
	}
}

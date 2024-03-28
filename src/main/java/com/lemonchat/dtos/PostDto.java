package com.lemonchat.dtos;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PostDto extends BasePostDto{
	
    private Long inReplyTo;
	
	private Set<BasePostDto> replies;
}

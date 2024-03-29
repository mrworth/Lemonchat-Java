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
	
    private Long inReplyTo;
	
	private Set<BasePostDto> replies;
}

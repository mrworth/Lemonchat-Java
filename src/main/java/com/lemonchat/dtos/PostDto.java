package com.lemonchat.dtos;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PostDto {
    private Long postId;
    
    private String title;
    private String topic;
    private String content;
    
    private PostDto parentPost;
    private Set<PostDto> replies;
}

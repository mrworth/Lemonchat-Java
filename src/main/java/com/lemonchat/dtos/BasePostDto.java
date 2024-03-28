package com.lemonchat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class BasePostDto {
    private Long postId;
    
    private String title;
    private String topic;
    private String content;
    
    @JsonIgnore
    private BasePostDto parentPost;
    
}

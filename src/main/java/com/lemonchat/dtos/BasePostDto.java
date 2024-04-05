package com.lemonchat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class BasePostDto {
    private Long postId;
    
    private Boolean hasReplies;
    
    @NotNull
    private String title;
    @NotNull
    private String topic;
    @NotNull
    private String content;
    
    @JsonIgnore
    private AccountDto account;
    
    private String username;
    
    @JsonIgnore
    private BasePostDto parentPost;
    
}

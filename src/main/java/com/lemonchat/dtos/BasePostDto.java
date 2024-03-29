package com.lemonchat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemonchat.entities.Account;

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
    
    @NotNull
    private String title;
    private String topic;
    private String content;
    
    @JsonIgnore
    private AccountDto account;
    
    private String username;
    
    @JsonIgnore
    private BasePostDto parentPost;
    
}

package com.lemonchat.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.lemonchat.dtos.BasePostDto;
import com.lemonchat.dtos.PostDto;
import com.lemonchat.entities.BasePost;
import com.lemonchat.entities.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "parentPost", ignore = true)
    @Mapping(target="username", source="account.username")
    BasePostDto postToPostDto(BasePost post);

    BasePost postDtoToPost(BasePostDto postDto);
    
    PostDto postToPostDto(Post post);
    
    @Mapping(target="username", source="account.username")
    Post postDtoToPost(PostDto postDto);

}

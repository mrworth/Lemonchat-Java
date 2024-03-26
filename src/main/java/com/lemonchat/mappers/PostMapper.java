package com.lemonchat.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.lemonchat.dtos.PostDto;
import com.lemonchat.entities.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(target = "parentPost", ignore = true) // Ignore or handle carefully
    PostDto postToPostDto(Post post);

    Post postDtoToPost(PostDto postDto);

}

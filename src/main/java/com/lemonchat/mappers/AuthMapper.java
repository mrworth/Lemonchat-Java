package com.lemonchat.mappers;

import org.mapstruct.Mapper;

import com.lemonchat.dtos.AuthDto;
import com.lemonchat.entities.Auth;

@Mapper(componentModel = "spring")
public interface AuthMapper {
	 AuthDto authToAuthDto(Auth auth);
	 Auth authDtoToAuth(AuthDto authDto);
}

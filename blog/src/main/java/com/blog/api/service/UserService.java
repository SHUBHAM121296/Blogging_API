package com.blog.api.service;

import java.util.List;

import com.blog.api.entity.User;
import com.blog.api.payload.UserDto;

public interface UserService {

	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Integer userId);
	UserDto getUserById(Integer userId);
	void deleteUser(Integer userId);
	List<UserDto> getUsers();
	User dtoToUser(UserDto userDto);
	UserDto userToUserDto(User user);
	
	UserDto registerUser(UserDto userDto);
	
}

package com.blog.api.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.api.config.AppConstants;
import com.blog.api.entity.Role;
import com.blog.api.entity.User;
import com.blog.api.exception.ResourceNotFoundException;
import com.blog.api.payload.UserDto;
import com.blog.api.repository.RoleRepo;
import com.blog.api.repository.UserRepo;
import com.blog.api.service.UserService;

@Service
public class UserServieImpl implements UserService {

	@Autowired
	private UserRepo userRepository;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDto createUser(UserDto user) {
		User savedUser=userRepository.save(this.dtoToUser(user));
		return this.userToUserDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto,Integer userId) {
		User oldUser=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		oldUser.setAbout(userDto.getAbout());
		oldUser.setEmail(userDto.getEmail());
		oldUser.setName(userDto.getName());
		oldUser.setPassword(userDto.getPassword());
		oldUser=userRepository.save(oldUser);
		return this.userToUserDto(oldUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id" ,userId));
		UserDto userDto=userToUserDto(user);
		return userDto;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id" ,userId));
		userRepository.delete(user);
		}

	@Override
	public List<UserDto> getUsers() {
		// TODO Auto-generated method stub
		List<User> users=userRepository.findAll();
		List<UserDto> userDto=new ArrayList<>();
		for(User user:users) {
			userDto.add(userToUserDto(user));
		}
		return userDto;
	}
	
	public User dtoToUser(UserDto userDto) {
		User user=modelmapper.map(userDto,User.class);
//		User user=new User();
//		user.setAbout(userDto.getAbout());
//		user.setEmail(userDto.getEmail());
//		user.setName(userDto.getName());
//		user.setPassword(userDto.getPassword());
//		user.setUserId(userDto.getUserId());
		return user;
	}
	
	public UserDto userToUserDto(User user) {
		UserDto userDto=modelmapper.map(user, UserDto.class);
//		userDto.setAbout(user.getAbout());
//		userDto.setEmail(user.getEmail());
//		userDto.setName(user.getName());
//		userDto.setPassword(user.getPassword());
//		userDto.setUserId(user.getUserId());
		return userDto;
	}

	@Override
	public UserDto registerUser(UserDto userDto) {
		User user=modelmapper.map(userDto,User.class);
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		Role role=roleRepo.findById(AppConstants.GENERIC_USER).get();
		user.getUser_roles().add(role);
		User savedUser=userRepository.save(user);
		return modelmapper.map(savedUser,UserDto.class);
	}

}

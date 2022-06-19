package com.blog.api.payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.blog.api.entity.Role;



public class UserDto {

	@Autowired
	private ModelMapper modelMapper;
	
	private int userId;
	@NotEmpty
	@Size(min=4,message="User name should be minimum of 4 characters")
	private String name;
	@Email(message="Email address is not valid")
	private String email;
	@NotEmpty
	@Size(min=8,message="Password should be minimum of 8 characters")
	private String password;
	@NotEmpty
	@Size(min=8,message="Tell us about you in more than 8 words  ")
	private String about;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}

	
	
	
}

package com.blog.api.payload;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import com.blog.api.entity.Category;
import com.blog.api.entity.User;


public class PostDto {

	public PostDto() {
		
	}
	private int postId;
	
	@NotEmpty
	private String title;
	@NotEmpty
	private String content;
	
	private String imageName;
	private Date addedDate;
	private CategoryDto category;
	private UserDto user;
	
	private Set<CommentDto> comments=new HashSet<>();
	
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public Date getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}
	public CategoryDto getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		CategoryDto categoryDto=new CategoryDto();
		categoryDto.setCategoryDescription(category.getCategoryDescription());
		categoryDto.setCategoryId(category.getCategoryId());
		categoryDto.setCategoryTitle(category.getCategoryTitle());
		this.category = categoryDto;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(User user) {
		UserDto userDto=new UserDto();
		userDto.setAbout(user.getAbout());
		userDto.setEmail(user.getEmail());
		userDto.setName(user.getName());
		userDto.setUserId(user.getUserId());
		userDto.setPassword(user.getPassword());
		this.user = userDto;
	}
	public Set<CommentDto> getComments() {
		return comments;
	}
	public void setComments(Set<CommentDto> comments) {
		this.comments = comments;
	}
	
}

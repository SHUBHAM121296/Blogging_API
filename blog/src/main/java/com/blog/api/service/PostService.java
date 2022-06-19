package com.blog.api.service;

import java.util.List;

import com.blog.api.payload.PostDto;
import com.blog.api.payload.PostResponse;

public interface PostService {
	
	// creating new post
	PostDto createPost(PostDto postDto,int categoryId,int userId);
	
	// updating existing post
	PostDto updatePost(PostDto postDto,int post_id);
	
	// deleting existing post
	void deletePost(int post_id);
	
	// get all posts
	PostResponse getAllPost(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	// getting single post 
	PostDto getPostById(int post_id);
	
	// getting all posts of a category
	List<PostDto> getPostByCategory(int categoryid);
	
	// getting posts made by a particular user
	List<PostDto> getPostByUser(int userId);
	
	// searching post by it's contents
	List<PostDto> searchPosts(String keyword);
	
	

}

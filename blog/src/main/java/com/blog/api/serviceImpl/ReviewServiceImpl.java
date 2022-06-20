package com.blog.api.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.entity.Category;
import com.blog.api.entity.Comment;
import com.blog.api.entity.Post;
import com.blog.api.entity.ReviewPost;
import com.blog.api.entity.User;
import com.blog.api.exception.ResourceNotFoundException;
import com.blog.api.payload.ReviewPostDto;
import com.blog.api.repository.CategoryRepo;
import com.blog.api.repository.PostRepo;
import com.blog.api.repository.ReviewPostRepo;
import com.blog.api.repository.UserRepo;
import com.blog.api.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private UserRepo userRepository;
	
	@Autowired
	private CategoryRepo categoryRepository;
	
	@Autowired
	private ReviewPostRepo reviewPostRepo;
	
	@Autowired
	private PostRepo postRepository;
	
	@Override
	public ReviewPost createPost(ReviewPostDto postDto,int categoryId, int userId) {
		userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","category-Id",categoryId));
		ReviewPost post=new ReviewPost();
		post.setCategoryId(categoryId);
		post.setContent(postDto.getContent());
		post.setStatus(0);
		post.setTitle(postDto.getTitle());
		post.setUserId(userId);
		ReviewPost createdPost=reviewPostRepo.save(post);
		return createdPost;
	}

	@Override
	public int updateStatus(int postId,int updatedStatus) {
		ReviewPost post=reviewPostRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Post Id for admin",postId));
		if(updatedStatus==2) {
			reviewPostRepo.deleteById(postId);
			return 0;
		}
		Post newPost=new Post();
		newPost.setAddedDate(new Date());
		User user=userRepository.findById(post.getUserId()).orElseThrow(()->new ResourceNotFoundException("User", "Id", post.getUserId()));
		Category category=categoryRepository.findById(post.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("Category","category-Id",post.getCategoryId()));
		newPost.setCategory(category);
		newPost.setImageName("default.png");
		newPost.setUser(user);
		Set<Comment> comments=new HashSet<>();
		newPost.setComments(comments);
		newPost.setContent(post.getContent());
		newPost.setTitle(post.getTitle());
		postRepository.save(newPost);
		reviewPostRepo.deleteById(postId);
		return 1;
	}

	@Override
	public List<ReviewPost> getAllPosts() {
		return reviewPostRepo.findAll();
	}
}
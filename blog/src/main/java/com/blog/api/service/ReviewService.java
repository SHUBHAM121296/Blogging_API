package com.blog.api.service;


import java.util.List;

import com.blog.api.entity.ReviewPost;
import com.blog.api.payload.ReviewPostDto;

public interface ReviewService {
	
	ReviewPost createPost(ReviewPostDto post,int categoryId, int userId);
	
	int updateStatus(int postId,int updatedStatus);
	
	List<ReviewPost> getAllPosts();

}

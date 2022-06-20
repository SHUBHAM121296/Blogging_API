package com.blog.api.payload;

import com.blog.api.entity.ReviewPost;

public class PostApiResponse {

	public PostApiResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PostApiResponse(String message, ReviewPost reviewPost) {
		super();
		this.message = message;
		this.reviewPost = reviewPost;
	}

	private String message;
	
	private ReviewPost reviewPost;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ReviewPost getReviewPostDto() {
		return reviewPost;
	}

	public void setReviewPost(ReviewPost reviewPost) {
		this.reviewPost = reviewPost;
	}
	
	
}

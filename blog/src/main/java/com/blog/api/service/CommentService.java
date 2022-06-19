package com.blog.api.service;

import com.blog.api.payload.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto,Integer postId,Integer userId);
	
	void deleteComment(Integer commentId,String username);
	

}

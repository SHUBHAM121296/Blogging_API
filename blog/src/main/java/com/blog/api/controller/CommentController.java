package com.blog.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.payload.ApiResponse;
import com.blog.api.payload.CommentDto;
import com.blog.api.service.CommentService;
import com.blog.api.service.UserService;

@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService ;
	
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(
			@RequestBody CommentDto commentDto ,
			@PathVariable Integer postId ,
			HttpServletRequest request
			){
		String requestToken = request.getHeader("Authorization");
		String username=userService.getUserFromToken(requestToken);
		int userId=userService.getUserIdfromUserName(username);
		CommentDto createComment = commentService.createComment(commentDto,postId,userId);
		return new ResponseEntity<CommentDto>(createComment,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(
			@PathVariable Integer commentId ,
			HttpServletRequest request
			){
		String requestToken = request.getHeader("Authorization");
		String username=userService.getUserFromToken(requestToken);
		System.out.println("I am user from the token in post : "+username);
		commentService.deleteComment(commentId,username);
		ApiResponse response=new ApiResponse("Comment Successfully deleted" , true);
		return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
	}

}

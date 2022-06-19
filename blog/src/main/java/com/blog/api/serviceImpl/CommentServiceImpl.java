package com.blog.api.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.entity.Comment;
import com.blog.api.entity.Post;
import com.blog.api.entity.User;
import com.blog.api.exception.ResourceNotFoundException;
import com.blog.api.payload.CommentDto;
import com.blog.api.payload.UserDto;
import com.blog.api.repository.CommentRepo;
import com.blog.api.repository.PostRepo;
import com.blog.api.repository.UserRepo;
import com.blog.api.security.JwtTokenHelper;
import com.blog.api.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId,Integer  userId) {
		Post post=postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
		User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User Id",postId));
		Comment comment=modelMapper.map(commentDto,Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		Comment savedComment=commentRepo.save(comment);
		return modelMapper.map(savedComment,CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId,String username) {
		Comment comment=commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "commentId",commentId));
		//User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId",userId));
		if(!username.equals(comment.getUser().getEmail())) {
			throw new ResourceNotFoundException("User not allowed to delete the post ","username "+username,0);
		}
		commentRepo.delete(comment);
	}

}

package com.blog.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.entity.ReviewPost;
import com.blog.api.payload.ApiResponse;
import com.blog.api.service.ReviewService;

@RestController
@RequestMapping("/api")
public class AdminController {

	@Autowired
	private ReviewService reviewService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin/review")
	public ResponseEntity<List<ReviewPost> > postsForReview(){
		List<ReviewPost> postList=reviewService.getAllPosts();
		return new ResponseEntity<List<ReviewPost>>(postList,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/admin/review/status/{postId}/{updatedStatus}")
	public ResponseEntity<ApiResponse> updateStatus(
			@PathVariable Integer postId ,
			@PathVariable Integer updatedStatus
			){
		int res=reviewService.updateStatus(postId,updatedStatus);
		ApiResponse respo=new ApiResponse();
		if(res==0) {
			respo.setMessage("Post Deleted Successfully ");
			respo.setSuccess(true);
		}else {
			respo.setMessage("Post Created Successfully ");
			respo.setSuccess(true);
		}
		return new ResponseEntity<ApiResponse>(respo,HttpStatus.OK);
	}
	
	
}

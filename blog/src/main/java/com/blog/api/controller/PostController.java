package com.blog.api.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.api.config.AppConstants;
import com.blog.api.payload.ApiResponse;
import com.blog.api.payload.PostDto;
import com.blog.api.payload.PostResponse;
import com.blog.api.security.JwtTokenHelper;
import com.blog.api.service.FileService;
import com.blog.api.service.PostService;
import com.blog.api.service.UserService;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
//	@Autowired
//	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserService userService;

	@PostMapping(value="/user/category/{categoryId}/post",consumes={"application/json"})
	public ResponseEntity<PostDto> createPost(
			@Valid @RequestBody PostDto postDto,
			@PathVariable int categoryId,
			HttpServletRequest request
		){
			String requestToken = request.getHeader("Authorization");
			String username=userService.getUserFromToken(requestToken);
			int userId=userService.getUserIdfromUserName(username);
			PostDto createdPost=postService.createPost(postDto, categoryId, userId);
			return new ResponseEntity<PostDto>(createdPost,HttpStatus.CREATED);
	}
	
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPost(@PathVariable int postId){
		PostDto postDto=postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.FOUND);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false) int pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false) String sortBy ,
			@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false) String sortDir 
//			HttpServletRequest request
			){
//		String requestToken = request.getHeader("Authorization");
//		String token = requestToken.substring(7);
//		String username = this.jwtTokenHelper.getUserNameFromToken(token);
//		System.out.println("I am user from the token in post : "+username);
		PostResponse postResponse=postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse >(postResponse,HttpStatus.OK);
	}
	
	@GetMapping("/user/{id}/posts")
	public ResponseEntity<List<PostDto>> getPostOfUser(@PathVariable int id,
			@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false) int pageNumber ,
			@RequestParam(value="PageSize",defaultValue=AppConstants.PAGE_SIZE,required=false) int pageSize
			){
	List<PostDto> posts=postService.getPostByUser(id);
	return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	@GetMapping("/category/{id}/posts")
	public ResponseEntity<List<PostDto>> getPostForCategory(@PathVariable int id){
		List<PostDto> posts=postService.getPostByCategory(id);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	@PutMapping("/posts/update/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable int postId){
		PostDto updatedPost=postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/delete/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable int postId){
		postService.deletePost(postId);
		ApiResponse resp=new ApiResponse("Post deleted Successfully ",true);
		return new ResponseEntity<ApiResponse>(resp,HttpStatus.OK);
	}
	
	//search
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(
			@PathVariable("keywords") String keywords
			){
		List<PostDto> postsDto = postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(postsDto,HttpStatus.OK);
	}
	
	// Post Image Upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostsImage(
			@RequestParam("image") MultipartFile image ,
			@PathVariable int postId
			) throws IOException{
		PostDto postDto = postService.getPostById(postId);
		String uploadImage = fileService.uploadImage(path, image);
		postDto.setImageName(uploadImage);
		PostDto updatedPost = postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
	
	
	@GetMapping(value="/post/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response
			) throws IOException {
		InputStream resource = fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	}
	
	
}

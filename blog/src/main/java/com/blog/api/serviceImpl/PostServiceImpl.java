package com.blog.api.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.api.entity.Category;
import com.blog.api.entity.Post;
import com.blog.api.entity.User;
import com.blog.api.exception.ResourceNotFoundException;
import com.blog.api.payload.PostDto;
import com.blog.api.payload.PostResponse;
import com.blog.api.repository.CategoryRepo;
import com.blog.api.repository.PostRepo;
import com.blog.api.repository.UserRepo;
import com.blog.api.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepository;
	
	@Autowired
	private UserRepo userRepository;
	
	@Autowired
	private CategoryRepo categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postDto, int categoryId, int userId) {
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","category-Id",categoryId));
		Post post=modelMapper.map(postDto, Post.class);
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setImageName("default.png");
		post.setUser(user);
		Post createdPost=postRepository.save(post);
		PostDto createdDtoPost=modelMapper.map(createdPost,PostDto.class);
		return createdDtoPost;
	}

	@Override
	public PostDto updatePost(PostDto postDto, int post_id) {
		Post post=postRepository.findById(post_id).orElseThrow(()->new ResourceNotFoundException("Post","Post-Id", post_id));
		post.setCategory(modelMapper.map(postDto.getCategory(),Category.class));
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		post.setTitle(postDto.getTitle());
		post.setUser(modelMapper.map(postDto.getUser(),User.class));
		Post updatedPost=postRepository.save(post);
		return modelMapper.map(updatedPost,PostDto.class);
	}

	@Override
	public void deletePost(int post_id) {
		Post post=postRepository.findById(post_id).orElseThrow(()->new ResourceNotFoundException("Post","Post-Id", post_id));
		postRepository.delete(post);
	}

	@Override
	public PostResponse getAllPost(int pageNumber,int pageSize,String sortBy,String sortDir) {
		
//		int pageSize=5;
//		int pageNumber=1;
		// sortBy is the variable which contain the fields wit which we want to sort
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}else {
			sort=Sort.by(sortBy).descending();
		}
		Pageable p=PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> pagePost=postRepository.findAll(p);
		List<Post> posts=pagePost.getContent();
		List<PostDto> dtoPosts=new ArrayList<>();
		posts.stream().forEach((post)-> dtoPosts.add(modelMapper.map(post,PostDto.class)));
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(dtoPosts);
		postResponse.setPageNumber(pageNumber);
		postResponse.setPageSize(pageSize);
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(int post_id) {
		Post post=postRepository.findById(post_id).orElseThrow(()->new ResourceNotFoundException("Post","Post-Id", post_id));
		PostDto postDto=modelMapper.map(post,PostDto.class);
		return postDto;
	}

	@Override
	public List<PostDto> getPostByCategory(int categoryId) {
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","category-Id",categoryId));
		List<Post> posts=postRepository.findByCategory(category);
		List<PostDto> dtoPosts=new ArrayList<PostDto>();
		posts.stream().forEach((post)->dtoPosts.add(modelMapper.map(post, PostDto.class)));
		return dtoPosts;
	}

	@Override
	public List<PostDto> getPostByUser(int userId) {
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
//		Pageable paging=PageRequest.of(0, 1);
		List<Post> posts=postRepository.findByUser(user);
		List<PostDto> dtoPosts=new ArrayList<PostDto>();
		posts.stream().forEach((post)->dtoPosts.add(modelMapper.map(post, PostDto.class)));
		return dtoPosts;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepository.findByTitleContaining(keyword);
		List<PostDto> postsDto = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postsDto;
	}


}

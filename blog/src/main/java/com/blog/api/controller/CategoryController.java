package com.blog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.payload.ApiResponse;
import com.blog.api.payload.CategoryDto;
import com.blog.api.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto savedCategory = categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(savedCategory,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		List<CategoryDto> categories=categoryService.getCategories();
		return new ResponseEntity<List<CategoryDto>>(categories,HttpStatus.OK);
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") int categoryId){
		CategoryDto category=categoryService.getCategory(categoryId);
		return new ResponseEntity<CategoryDto>(category,HttpStatus.FOUND);
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateUser(@Valid @RequestBody CategoryDto categoryDto , @PathVariable("categoryId") int categoryId ){
		CategoryDto updatedCategory=categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.OK);
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") int categoryId){
		categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(new ApiResponse("Category has been deleted",true),HttpStatus.OK);
	}
	
	
}

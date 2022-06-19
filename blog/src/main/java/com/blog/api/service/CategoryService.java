package com.blog.api.service;

import java.util.List;

import com.blog.api.payload.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);
	
	CategoryDto updateCategory(CategoryDto categoryDto,int categoryId);
	
	void deleteCategory(int categoryId);
	
	CategoryDto getCategory(int categoryId);
	
	List<CategoryDto> getCategories();
	
}

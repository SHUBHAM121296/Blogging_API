package com.blog.api.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.entity.Category;
import com.blog.api.exception.ResourceNotFoundException;
import com.blog.api.payload.CategoryDto;
import com.blog.api.repository.CategoryRepo;
import com.blog.api.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category=modelMapper.map(categoryDto,Category.class);
		Category savedCategory= categoryRepository.save(category);
		return modelMapper.map(savedCategory,CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId) {
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category-Id", categoryId));
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		Category savedCategory=categoryRepository.save(category);
		return modelMapper.map(savedCategory,CategoryDto.class);
	}

	@Override
	public void deleteCategory(int categoryId) {
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category-Id", categoryId));
		categoryRepository.delete(category);
	}

	@Override
	public CategoryDto getCategory(int categoryId) {
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category-Id", categoryId));
		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> categories=categoryRepository.findAll();
		List<CategoryDto> categoriesDto=new ArrayList<>();
		categories.stream().forEach((category)->categoriesDto.add(modelMapper.map(category,CategoryDto.class)));
		return categoriesDto;
	}

}

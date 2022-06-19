package com.blog.api.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CategoryDto {
	
	public CategoryDto() {
	}

	
	private int categoryId;
	
	@NotEmpty
	@Size(min=8,message="Category Title should be minimum of 8 characters")
	private String categoryTitle;
	
	@NotEmpty
	@Size(min=15,message="Category description should be minimum of 15 characters")
	private String categoryDescription;

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

}

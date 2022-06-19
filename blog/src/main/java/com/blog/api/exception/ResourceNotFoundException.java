package com.blog.api.exception;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException {
	
	String resourceName;
	String resourceType;
	int fieldValue;
	
	public ResourceNotFoundException(String resourceName,String resourceType,int fieldValue) {
		super(String.format("%s not found with %s : %s",resourceName,resourceType,fieldValue));
		this.resourceName=resourceName;
		this.resourceType=resourceType;
		this.fieldValue=fieldValue;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public int getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(int fieldValue) {
		this.fieldValue = fieldValue;
	}

}

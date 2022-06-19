package com.blog.api.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	// (where we want to store the data,data that we will get in MultipartFile)
	String uploadImage(String path,MultipartFile file) throws IOException;
	
	InputStream getResource(String path,String fileName) throws FileNotFoundException;
	
}

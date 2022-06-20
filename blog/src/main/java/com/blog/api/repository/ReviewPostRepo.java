package com.blog.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.api.entity.ReviewPost;

public interface ReviewPostRepo extends JpaRepository<ReviewPost, Integer> {

}

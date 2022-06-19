package com.blog.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.api.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}

package com.blog.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.api.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{
	
}

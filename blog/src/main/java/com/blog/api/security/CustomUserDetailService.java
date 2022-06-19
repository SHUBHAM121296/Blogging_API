package com.blog.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.api.entity.User;
import com.blog.api.exception.ResourceNotFoundException;
import com.blog.api.repository.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

		@Autowired
		private UserRepo userRepo;

		// whenever spring security want to fetch user details it call this method
		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

			// loading user from database by username
			User user = userRepo.findByEmail(username)
					.orElseThrow(() -> new ResourceNotFoundException("User ", " email : " + username, 0));

			return user;
		}

	}


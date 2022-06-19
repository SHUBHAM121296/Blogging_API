package com.blog.api.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Role {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	
//	@OneToMany(mappedBy = "role",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//	private Set<User_role> userRoles=new HashSet<>();
	@ManyToMany(mappedBy ="roles",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private Set<User> users=new HashSet<>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<User> getUserRoles() {
		return users;
	}
	public void setUserRoles(Set<User> userRoles) {
		this.users = userRoles;
	}
	
}

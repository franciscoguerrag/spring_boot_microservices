package com.formacionbdi.springboot.app.users.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.formacionbdi.springboot.app.users.models.entity.User;

@RepositoryRestResource(path="users")
public interface UserDao extends PagingAndSortingRepository<User, Long>{
	
	//public User findByUsernameAndEmail(String username, String email);  query method call that
	@RestResource(path = "search-username")  //optional for name method
	public User findByUsername(@Param("name") String username);  //query method
	
	@Query("select u from User u where u.username = ?1")  // other way  ,  also you can use native query
	public User getByUsername(String username);


}

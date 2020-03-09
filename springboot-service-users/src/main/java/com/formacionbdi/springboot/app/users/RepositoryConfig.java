package com.formacionbdi.springboot.app.users;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import com.formacionbdi.springboot.app.users.models.entity.Role;
import com.formacionbdi.springboot.app.users.models.entity.User;



@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer{

	@Override  //this is just for the ids
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(User.class, Role.class);
	}

	
	
}

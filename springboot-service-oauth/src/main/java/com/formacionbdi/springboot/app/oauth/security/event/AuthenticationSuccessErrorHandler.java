package com.formacionbdi.springboot.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.formacionbdi.springboot.app.oauth.services.IUserService;
import com.formacionbdi.springboot.app.users.commons.models.entity.User;

import brave.Tracer;
import feign.FeignException;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher{

	private Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private Tracer tracer;

	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		String msg= "Success Login" + user.getUsername();
		System.out.println(msg);
		log.info(msg);
		
		User userd = userService.findByUsername(authentication.getName());
		if(userd.getIntentos()!=null && userd.getIntentos()>0) {
			userd.setIntentos(0);
			userService.update(userd, userd.getId());
		}
		
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String msg= "Error en el Login: " + exception.getMessage();
		System.out.println(msg);
		log.error(msg);
		
		try {
			StringBuilder errors= new  StringBuilder();
			errors.append(msg);
			
			User user = userService.findByUsername(authentication.getName());
			if(user.getIntentos()==null) {
				user.setIntentos(0);
			}
			log.info("Intentos actual es de: " +user.getIntentos() );
			user.setIntentos(user.getIntentos()+1);
			log.info("Intentos despues es de: " +user.getIntentos() );
			errors.append(" _ " +"Intentos de login es de: " +user.getIntentos());
			
			if(user.getIntentos()>=3) {
				String errorMax ="El usuario %s des-habilitado por máximos intentos." +  user.getUsername();
				log.error(errorMax);
				errors.append(" _ " +errorMax);
				
				user.setEnabled(false);
			}
			userService.update(user, user.getId());
			tracer.currentSpan().tag("error.mensaje", errors.toString());
		}
		catch (FeignException e) {
			log.error(String.format("El usuario %s no existe en el sistema" , authentication.getName()));
		}
		
		
	}

}

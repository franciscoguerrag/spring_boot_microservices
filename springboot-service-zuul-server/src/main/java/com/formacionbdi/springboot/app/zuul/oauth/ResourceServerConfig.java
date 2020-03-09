package com.formacionbdi.springboot.app.zuul.oauth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@RefreshScope
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	@Value("${config.security.oauth.jwt.key}")
	private String jwtKey;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		 http.authorizeRequests().antMatchers("/api/security/oauth/**").permitAll()
		 .antMatchers(HttpMethod.GET, "/api/products/listar", "/api/items/listar", "/api/users/users").permitAll()
		 .antMatchers(HttpMethod.GET, "/api/products/ver/{id}",
				 "/api/items/ver/{id}/quantity/{quantity",
				 "/api/users/users/{id}").hasAnyRole("ADMIN","USER")
//		 .antMatchers(HttpMethod.POST, "/api/products/crear", "/api/items/create", "/api/users/users").hasRole("ADMIN")
//		 .antMatchers(HttpMethod.PUT, "/api/products/editar/{id}", "/api/items/edit/{id}", "/api/users/users/{id}").hasRole("ADMIN")
//		 .antMatchers(HttpMethod.DELETE, "/api/products/eliminar/{id}", "/api/items/delete/{id}", "/api/users/users/{id}").hasRole("ADMIN");
		 .antMatchers("/api/products/**", "/api/items/**", "/api/users/**").hasRole("ADMIN")
		 .anyRequest().authenticated() // other way at the end
		 .and()
		 .cors().configurationSource(CorsConfigurationSource()); 
		 
	}
	
	@Bean
	public CorsConfigurationSource CorsConfigurationSource() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(Arrays.asList("*"));
		corsConfig.setAllowedMethods(Arrays.asList("POST","GET","PUT","DELETE","OPTIONS"));
		corsConfig.setAllowCredentials(true);
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
		UrlBasedCorsConfigurationSource source  = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		FilterRegistrationBean<CorsFilter> bean =
				new FilterRegistrationBean<CorsFilter>(new CorsFilter(CorsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
	
	
	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter= new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(jwtKey);
		return tokenConverter;
	}

}

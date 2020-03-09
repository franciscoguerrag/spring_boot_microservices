package com.formacionbdi.springboot.app.item.models.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.commons.models.entity.Product;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private RestTemplate clientRest;

	@Override
	public List<Item> findAll() {
		List<Product> products = Arrays.asList(clientRest.getForObject("http://service-products/listar", Product[].class));
		return products.stream().map(p -> new Item(p,1)).collect(Collectors.toList() );
	}

	@Override
	public Item findById(Long id, Integer quantity) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		Product product = clientRest.getForObject("http://service-products/ver/{id}", Product.class, pathVariables);
		return new Item(product,quantity);
	}

	@Override
	public Product save(Product product) {
		HttpEntity<Product> body= new HttpEntity<Product>(product);
		ResponseEntity<Product> response = clientRest.exchange("http://service-products/crear", HttpMethod.POST, body, Product.class);
		return response.getBody();
	}

	@Override
	public Product update(Product product, Long id) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		HttpEntity<Product> body = new HttpEntity<Product>(product);
		ResponseEntity<Product> response = clientRest.exchange("http://service-products/editar/{id}", 
				HttpMethod.PUT, body, Product.class, pathVariables);
		return response.getBody();
	}

	@Override
	public void delete(Long id) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		clientRest.delete("http://service-products/eliminar/{id}", pathVariables);		
	}

}

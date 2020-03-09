package com.formacionbdi.springboot.app.item.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.commons.models.entity.Product;
import com.formacionbdi.springboot.app.item.models.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RefreshScope  //actualizar componentes valueconfigurations
@RestController
public class ItemController {
	
	public static Logger log = LoggerFactory.getLogger(ItemController.class);
	
	@Autowired
	private Environment env;

	@Autowired
	@Qualifier("serviceFeign")  //es otra opcion a primary, en este caso vamos a usar feign
	//@Qualifier("serviceRestTemplate")
	private ItemService itemService;
	
	@Value("${configuration.text}")
	private String text;
	
	@GetMapping("/listar")
	public List<Item> listar() {
		return itemService.findAll();
	}

	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@GetMapping("ver/{id}/quantity/{quantity}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer quantity) {
		return itemService.findById(id, quantity);
	}
	
	public Item metodoAlternativo(Long id, Integer quantity) {
		Item item= new Item();
		Product product= new Product();
		item.setQuantity(quantity);
		product.setId(id);
		product.setName("sony camera default");
		product.setPrice(2323.22);
		item.setProduct(product);
		return item;
	}
	
	@GetMapping("/obtener-config")
	public ResponseEntity<?> obtenerConfig(@Value("${server.port}") String port) {
		log.info(text);
		Map<String, String> json = new HashMap<>();
		json.put("text", text);
		json.put("port", port);
		
		if(env.getActiveProfiles().length>0 && env.getActiveProfiles()[0].equals("dev")) {
			json.put("author.name", env.getProperty("configuration.author.name"));
			json.put("author.email", env.getProperty("configuration.author.email"));
		}
		
		return new ResponseEntity<Map<String, String>>(json,HttpStatus.OK);
	}
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public Product create(@RequestBody Product product) {
		return itemService.save(product);
	}
	
	@PutMapping("/edit/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Product edit(@RequestBody Product product, @PathVariable Long id) {
		return itemService.update(product, id);
	}
	
	@DeleteMapping("delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		itemService.delete(id);
	}
}

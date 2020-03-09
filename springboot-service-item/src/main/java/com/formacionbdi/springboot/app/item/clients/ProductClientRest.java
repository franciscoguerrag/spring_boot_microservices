package com.formacionbdi.springboot.app.item.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.formacionbdi.springboot.app.commons.models.entity.Product;

//@FeignClient(name = "service-products", url = "localhost:6001")  //before ribbon
@FeignClient(name = "service-products")  //with  ribbon
public interface ProductClientRest {
	
	@GetMapping("/listar")
	public List<Product> listar();
	
	@GetMapping("ver/{id}")
	public Product detalle(@PathVariable Long id);

	@PostMapping("/crear")
	public Product create(@RequestBody Product product);
	
	@PutMapping("/editar/{id}")
	public Product update(@RequestBody Product product, @PathVariable Long id);
	
	@DeleteMapping("/eliminar/{id}")
	public Product delete(@PathVariable Long id);
	
}
